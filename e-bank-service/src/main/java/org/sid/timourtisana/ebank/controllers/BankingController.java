package org.sid.timourtisana.ebank.controllers;
import org.sid.timourtisana.ebank.clients.CustomerClient;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/banking")
public class BankingController {
 private final CustomerClient customerClient;
 private final CircuitBreakerFactory<?,?> circuitBreakerFactory;
 public BankingController(CustomerClient customerClient, CircuitBreakerFactory<?,?> circuitBreakerFactory) {
  this.customerClient=customerClient; this.circuitBreakerFactory=circuitBreakerFactory;
 }
 @GetMapping("/customers/{id}")
 public Map<String,Object> customer(@PathVariable Long id) {
  return circuitBreakerFactory.create("customerService").run(
   () -> customerClient.findCustomerById(id),
   error -> Map.of("message","Customer service temporarily unavailable","customerId",id));
 }
}