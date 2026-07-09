package com.training.lab2a;

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
class TravelControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldSearchFlights() throws Exception {
        mockMvc.perform(get("/v1/flights")
                        .param("origin", "JFK")
                        .param("destination", "LAX")
                        .param("departureDate", "2026-07-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].flightNumber").value("FL-101"));
    }

    @Test
    void shouldCreateBooking() throws Exception {
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
    }
}
