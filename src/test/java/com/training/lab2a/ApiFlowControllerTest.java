package com.training.lab2a;

import com.training.lab2a.repository.BookingRepository;
import com.training.lab2a.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ApiFlowControllerTest {

    @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
      this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        bookingRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void shouldSearchFlightsAndFetchFlightDetails() throws Exception {
        mockMvc.perform(get("/v1/flights")
                        .param("origin", "JFK")
                        .param("destination", "LAX")
                        .param("departureDate", "2026-07-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].flightNumber").value("FL-101"));

        mockMvc.perform(get("/v1/flights/FL-101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("FL-101"));
    }

    @Test
    void shouldCreateAndCancelBooking() throws Exception {
        String payload = """
                {
                  "status": "PENDING",
                  "flight": {
                    "flightNumber": "FL-101",
                    "origin": {
                      "code": "JFK",
                      "name": "John F. Kennedy International Airport",
                      "city": "New York",
                      "timezone": "America/New_York"
                    },
                    "destination": {
                      "code": "LAX",
                      "name": "Los Angeles International Airport",
                      "city": "Los Angeles",
                      "timezone": "America/Los_Angeles"
                    },
                    "departure": "2026-07-20T10:30:00",
                    "arrival": "2026-07-20T13:45:00",
                    "status": "SCHEDULED",
                    "seatsAvailable": 12,
                    "price": 320.5
                  },
                  "passengers": [
                    {
                      "name": "Jane Doe",
                      "seat": "12A",
                      "type": "ADULT"
                    }
                  ],
                  "payment": {
                    "amount": 320.5,
                    "currency": "USD",
                    "status": "PENDING"
                  }
                }
                """;

        mockMvc.perform(post("/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"));

        mockMvc.perform(get("/v1/bookings/BK-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"));

        mockMvc.perform(post("/v1/bookings/1/cancellation"))
                .andExpect(status().isOk());
    }
}