package com.example.bookstore.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private List<BookItemDTO> items;
    private double totalPrice;

    public OrderDTO() {}

    public OrderDTO(Long orderId, Long customerId, List<BookItemDTO> items, double totalPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
