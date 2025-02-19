package com.example.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.entity.Customer;
import com.example.bookstore.exception.CustomerNotFoundException;
import com.example.bookstore.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get loyalty points of a customer
    public int getLoyaltyPoints(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(
                "Customer with ID " + customerId + " not found."));
        return customer.getLoyaltyPoints();
    }

}
