package org.sid.timourtisana.custumerservice.repositories;

import org.sid.timourtisana.custumerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
