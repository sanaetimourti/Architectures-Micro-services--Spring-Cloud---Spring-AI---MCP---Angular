package org.sid.timourtisana.ai;

import org.springframework.ai.tool.annotation.Tool;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
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
    @McpTool(name = "find_customer", description = "Retrieve a customer from the Customer microservice")
    public String findCustomer(
            @McpToolParam(description = "Customer identifier", required = true) Long customerId) {
        return customerApi.get()
                .uri("/api/customers/{id}", customerId)
                .retrieve()
                .body(String.class);
    }

    @Tool(description = "Retrieve customer banking information through the E-Bank microservice")
    @McpTool(name = "get_banking_customer", description = "Call the E-Bank microservice for a customer")
    public String getBankingCustomer(
            @McpToolParam(description = "Customer identifier", required = true) Long customerId) {
        return bankingApi.get()
                .uri("/api/banking/customers/{id}", customerId)
                .retrieve()
                .body(String.class);
    }
}