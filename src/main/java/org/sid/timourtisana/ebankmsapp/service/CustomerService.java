package org.sid.timourtisana.ebankmsapp.service;

import org.sid.timourtisana.ebankmsapp.entities.Customer;
import org.sid.timourtisana.ebankmsapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public Customer findCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(()->new RuntimeException("Customer Not found"));

    }
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
