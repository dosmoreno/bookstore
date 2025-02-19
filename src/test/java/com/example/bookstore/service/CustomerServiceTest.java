package com.example.bookstore.service;

import com.example.bookstore.entity.Customer;
import com.example.bookstore.exception.CustomerNotFoundException;
import com.example.bookstore.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnLoyaltyPointsForCustomer() {
        // Arrange
        Customer customer = new Customer(1L, "John Doe", 20);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        int points = customerService.getLoyaltyPoints(1L);

        // Assert
        assertEquals(20, points);
    }

    @Test
    void shouldThrowExceptionIfCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getLoyaltyPoints(1L);
        });

        assertEquals("Customer with ID 1 not found.", exception.getMessage());
    }
}
