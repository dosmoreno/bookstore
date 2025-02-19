package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.entity.*;
import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.exception.CustomerNotFoundException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CustomerRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.strategy.PricingStrategy;
import com.example.bookstore.service.strategy.PricingStrategyFactory;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final PricingStrategyFactory pricingStrategyFactory;
    private final ModelMapper modelMapper;

    public OrderService(BookRepository bookRepository, CustomerRepository customerRepository, OrderRepository orderRepository, PricingStrategyFactory pricingStrategyFactory, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.pricingStrategyFactory = pricingStrategyFactory;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public OrderDTO placeOrder(Long customerId, List<Long> bookIds) {
        logger.info("Placing order for customer with ID: {}", customerId);

        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        Customer customer = customerOpt.get();
        List<OrderItem> bookItems = new ArrayList<>();
        double totalPrice = 0.0;
        Map<Long, Integer> bookCounts = new HashMap<>();

        // Count the occurrences of each book
        for (Long bookId : bookIds) {
            bookCounts.put(bookId, bookCounts.getOrDefault(bookId, 0) + 1);
        }

        // Process each book in the order
        for (Map.Entry<Long, Integer> entry : bookCounts.entrySet()) {
            Long bookId = entry.getKey();
            int quantity = entry.getValue();

            Optional<Book> bookOpt = bookRepository.findById(bookId);
            if (bookOpt.isEmpty()) {
                throw new BookNotFoundException("Book not found: ID " + bookId);
            }

            Book book = bookOpt.get();
            PricingStrategy strategy = pricingStrategyFactory.getStrategy(book.getType());
            double price = strategy.calculatePrice(book, quantity);

            OrderItem bookItem = new OrderItem(book, quantity, price);
            bookItems.add(bookItem);

            totalPrice += price;
        }

        // Apply loyalty points discount if applicable
        if (customer.getLoyaltyPoints() >= 10) {
            totalPrice = applyLoyaltyDiscount(bookItems, totalPrice);
            customer.setLoyaltyPoints(0); // Reset loyalty points after discount is used
        } else {
            // Award loyalty points based on the number of books purchased
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() + bookIds.size());
        }

        // Create and save the order
        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(bookItems);
        order.setTotalPrice(totalPrice);

        customerRepository.save(customer);
        Order savedOrder = orderRepository.save(order);

        // Return OrderDTO
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    private double applyLoyaltyDiscount(List<OrderItem> orderItems, double totalPrice) {
        for (OrderItem item : orderItems) {
            if (item.getBook().getType() == BookType.REGULAR || item.getBook().getType() == BookType.OLD_EDITION) {
                totalPrice -= item.getDiscountedPrice(); // Apply discount on the first applicable book
                break;
            }
        }
        return totalPrice;
    }
}
