package com.training.lab2a;

import com.training.lab2a.entity.Customer;
import com.training.lab2a.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthControllerSecurityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    void shouldReturnForbiddenWhenAgentDeletesCustomer() throws Exception {
        Customer customer = customerRepository.save(new Customer(
                null,
                "Delete",
                "Target",
                "delete.target@example.com",
                "1234567890",
                "10 Main Street",
                "London",
                "UK"
        ));

        String agentToken = loginAndGetToken("agent");

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + "/v1/customers/" + customer.getId()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + agentToken)
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.FORBIDDEN.value(), deleteResponse.statusCode());
    }

    @Test
    void shouldAllowAdminToDeleteCustomer() throws Exception {
        Customer customer = customerRepository.save(new Customer(
                null,
                "Admin",
                "Delete",
                "admin.delete@example.com",
                "0987654321",
                "42 Market Road",
                "Paris",
                "FR"
        ));

        String adminToken = loginAndGetToken("admin");

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + "/v1/customers/" + customer.getId()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.NO_CONTENT.value(), deleteResponse.statusCode());
    }

    private String loginAndGetToken(String username) throws Exception {
        String payload = "{\"username\":\"" + username + "\"}";
        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + "/auth/login"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> loginResponse = httpClient.send(loginRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpStatus.OK.value(), loginResponse.statusCode());

        return extractAccessToken(loginResponse.body());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    private String extractAccessToken(String body) {
        String marker = "\"access_token\":\"";
        int start = body.indexOf(marker);
        if (start < 0) {
            throw new IllegalStateException("access_token not found in response: " + body);
        }

        int valueStart = start + marker.length();
        int valueEnd = body.indexOf('"', valueStart);
        if (valueEnd < 0) {
            throw new IllegalStateException("access_token end quote not found in response: " + body);
        }

        return body.substring(valueStart, valueEnd);
    }
}