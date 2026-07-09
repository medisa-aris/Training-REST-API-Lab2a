package com.training.lab2a;

import com.training.lab2a.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
                SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                                "admin",
                                                "n/a",
                                                AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_AGENT")
                                )
                );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

        @AfterEach
        void tearDown() {
                SecurityContextHolder.clearContext();
        }

    @Test
    void shouldCreateCustomer() throws Exception {
        String payload = """
                {
                  "firstName": "Jane",
                  "lastName": "Doe",
                  "email": "jane@example.com",
                  "phone": "1234567890",
                  "address": "10 Main Street",
                  "city": "London",
                  "country": "UK"
                }
                """;

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    void shouldReturnBadRequestForInvalidCustomer() throws Exception {
        String payload = """
                {
                  "firstName": "",
                  "lastName": "Doe",
                  "email": "not-an-email",
                  "phone": "1234567890",
                  "address": "10 Main Street",
                  "city": "London",
                  "country": "UK"
                }
                """;

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundForMissingCustomer() throws Exception {
        mockMvc.perform(get("/v1/customer/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        String payload = """
                {
                  "firstName": "John",
                  "lastName": "Smith",
                  "email": "john@example.com",
                  "phone": "0987654321",
                  "address": "42 Market Road",
                  "city": "Paris",
                  "country": "FR"
                }
                """;

        String response = mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Matcher matcher = Pattern.compile("\"id\":(\\d+)").matcher(response);
        matcher.find();
        Long customerId = Long.parseLong(matcher.group(1));

        mockMvc.perform(delete("/v1/customers/" + customerId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldSupportPageAndLimitPagination() throws Exception {
        customerRepository.saveAll(java.util.List.of(
                new com.training.lab2a.entity.Customer(null, "A", "One", "a@example.com", "1111111111", "1 Street", "City", "UK"),
                new com.training.lab2a.entity.Customer(null, "B", "Two", "b@example.com", "2222222222", "2 Street", "City", "UK"),
                new com.training.lab2a.entity.Customer(null, "C", "Three", "c@example.com", "3333333333", "3 Street", "City", "UK")
        ));

        mockMvc.perform(get("/v1/customers").param("page", "0").param("limit", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").exists())
                .andExpect(jsonPath("$.content[1].email").exists());
    }

    @Test
    void shouldSupportCursorPagination() throws Exception {
        customerRepository.saveAll(java.util.List.of(
                new com.training.lab2a.entity.Customer(null, "D", "Four", "d@example.com", "4444444444", "4 Street", "City", "UK"),
                new com.training.lab2a.entity.Customer(null, "E", "Five", "e@example.com", "5555555555", "5 Street", "City", "UK"),
                new com.training.lab2a.entity.Customer(null, "F", "Six", "f@example.com", "6666666666", "6 Street", "City", "UK")
        ));

        mockMvc.perform(get("/v1/customers").param("cursor", "0").param("limit", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].email").exists())
                .andExpect(jsonPath("$.items[1].email").exists());
    }
}
