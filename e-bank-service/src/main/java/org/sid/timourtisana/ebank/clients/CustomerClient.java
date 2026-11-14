package org.sid.timourtisana.ebank.clients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;
@FeignClient(name = "customer-service", path = "/api/customers")
public interface CustomerClient {
 @GetMapping("/{id}") Map<String,Object> findCustomerById(@PathVariable("id") Long id);
}