package com.akhil.w.customerservices.service;

import com.akhil.w.customerservices.domain.Customer;
import com.akhil.w.customerservices.web.dto.CustomerOrder;
import com.akhil.w.customerservices.repo.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * akhil - 10/2/2020 - 12:01 PM
 */
@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        super();
        this.repository = repository;
    }

    public List<Customer> findAllCustomers() {
        return this.repository.findAll();
    }

    public Customer findCustomer(long id) {
        return this.repository.findById(id).orElse(null);
    }

    public List<CustomerOrder> getCustomerOrders() {
        return new ArrayList<>();
    }

    public Customer save(Customer customer) {
        return this.repository.save(customer);
    }
}
