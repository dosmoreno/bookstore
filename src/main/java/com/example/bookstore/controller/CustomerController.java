package com.example.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.exception.CustomerNotFoundException;
import com.example.bookstore.service.CustomerService;
import com.example.bookstore.dto.CustomerDTO;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get loyalty points of a customer
    @GetMapping("/loyalty/{customerId}")
    public ResponseEntity<CustomerDTO> getLoyaltyPoints(@PathVariable Long customerId) {
        try {
            int points = customerService.getLoyaltyPoints(customerId);
            CustomerDTO customerDTO = new CustomerDTO(customerId, points);
            return ResponseEntity.ok(customerDTO);
        } catch (CustomerNotFoundException ex) {
            // Handle customer not found scenario
            return ResponseEntity.status(404).body(new CustomerDTO(customerId, 0));
        }
    }
}
