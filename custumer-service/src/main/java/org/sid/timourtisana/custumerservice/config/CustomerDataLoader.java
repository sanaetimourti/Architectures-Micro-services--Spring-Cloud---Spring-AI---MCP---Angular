package org.sid.timourtisana.custumerservice.config;
import org.sid.timourtisana.custumerservice.entities.Customer;
import org.sid.timourtisana.custumerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class CustomerDataLoader {
 @Bean
 CommandLineRunner loadCustomers(CustomerRepository repository) {
  return args -> {
   if (repository.count() == 0) {
    repository.save(new Customer("Sara Benali", "sara.benali@example.com"));
    repository.save(new Customer("Youssef Amrani", "youssef.amrani@example.com"));
   }
  };
 }
}