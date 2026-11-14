package org.sid.timourtisana.ai;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BankingMcpTools {
    private final RestClient customerApi = RestClient.builder()
            .baseUrl("http://localhost:8082")
            .build();
    private final RestClient bankingApi = RestClient.builder()
            .baseUrl("http://localhost:8083")
            .build();

    @Tool(description = "Find a customer by their numeric identifier")
    public String findCustomer(Long customerId) {
        return customerApi.get()
                .uri("/api/customers/{id}", customerId)
                .retrieve()
                .body(String.class);
    }

    @Tool(description = "List bank accounts belonging to a customer")
    public String listCustomerAccounts(Long customerId) {
        return bankingApi.get()
                .uri("/api/accounts/customer/{id}", customerId)
                .retrieve()
                .body(String.class);
    }

    @Tool(description = "Retrieve banking customer information through the E-Bank service")
    public String getBankingCustomer(Long customerId) {
        return bankingApi.get()
                .uri("/api/banking/customers/{id}", customerId)
                .retrieve()
                .body(String.class);
    }
}