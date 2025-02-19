package com.example.bookstore.dto;

import lombok.Data;

@Data
public class BookItemDTO {
    private Long bookId;
    private int quantity;
    private double price;

    public BookItemDTO() {}

    public BookItemDTO(Long bookId, int quantity, double price) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }
}
