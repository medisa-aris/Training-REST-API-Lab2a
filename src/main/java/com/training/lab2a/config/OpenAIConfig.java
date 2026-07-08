package com.training.lab2a.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Bean
    public OpenAPI acmeOpenAPI() {
        return new OpenAPI().info( new Info()
                .title("ACME Travel API")
                .version("1.0.0")
                .description("Enterprise Booking"));
    }
}
