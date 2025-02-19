package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.exception.CustomerNotFoundException;
import com.example.bookstore.service.OrderService;

import lombok.Data;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{customerId}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long customerId, @RequestBody OrderRequest orderRequest) {
        try {
            OrderDTO orderDTO = orderService.placeOrder(customerId, orderRequest.getBookIds());
            return ResponseEntity.ok(orderDTO);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Data
    public static class OrderRequest {
        private List<Long> bookIds;
    }
}
