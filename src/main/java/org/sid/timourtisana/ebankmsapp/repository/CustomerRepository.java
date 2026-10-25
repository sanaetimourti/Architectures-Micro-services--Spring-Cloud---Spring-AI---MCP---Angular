package org.sid.timourtisana.ebankmsapp.repository;

import org.sid.timourtisana.ebankmsapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
