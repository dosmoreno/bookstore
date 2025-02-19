package com.example.bookstore.service.strategy;

import com.example.bookstore.entity.Book;

public class RegularPricing implements PricingStrategy {
    @Override
    public double calculatePrice(Book book, int quantity) {
        return quantity >= 3 ? book.getPrice() * quantity * 0.9 : book.getPrice() * quantity;
    }
}
