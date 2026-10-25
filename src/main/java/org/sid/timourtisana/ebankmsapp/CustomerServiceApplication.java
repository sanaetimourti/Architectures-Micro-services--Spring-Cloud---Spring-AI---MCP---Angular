package org.sid.timourtisana.ebankmsapp;

import org.sid.timourtisana.ebankmsapp.controllers.CustomerRestController;
import org.sid.timourtisana.ebankmsapp.entities.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRestController customerRestController) {
        return args -> {
            // Votre code d'initialisation ici
        };
    }
}