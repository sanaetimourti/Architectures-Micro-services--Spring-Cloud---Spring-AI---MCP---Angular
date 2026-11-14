package org.sid.timourtisana.ai;
import org.springframework.ai.tool.annotation.Tool;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class BankingMcpTools {
 private final RestClient customerApi=RestClient.builder().baseUrl("http://localhost:8082").build();
 private final RestClient bankingApi=RestClient.builder().baseUrl("http://localhost:8083").build();
 @Tool(description="Find a customer by numeric identifier")
 @McpTool(name="find_customer",description="Retrieve a customer from Customer-Service")
 public String findCustomer(@McpToolParam(description="Customer identifier",required=true) Long customerId){
  return customerApi.get().uri("/api/customers/{id}",customerId).retrieve().body(String.class);
 }
 @Tool(description="List bank accounts for a customer")
 @McpTool(name="list_customer_accounts",description="List accounts belonging to a customer")
 public String listCustomerAccounts(@McpToolParam(description="Customer identifier",required=true) Long customerId){
  return bankingApi.get().uri("/api/accounts/customer/{id}",customerId).retrieve().body(String.class);
 }
 @Tool(description="Retrieve banking service information for a customer")
 @McpTool(name="get_banking_customer",description="Call the E-Bank service for a customer")
 public String getBankingCustomer(@McpToolParam(description="Customer identifier",required=true) Long customerId){
  return bankingApi.get().uri("/api/banking/customers/{id}",customerId).retrieve().body(String.class);
 }
}