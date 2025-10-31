package com.rabobank.banking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * OpenAPI configuration foor the Banking System API Doc.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rabobankBankingOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Banking System API")
                .description("Banking account management with withdrawal and transfer capabilities")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Rabobank Assignment")
                    .email("support@rabobank.nl")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Local Development Server")));
    }
}