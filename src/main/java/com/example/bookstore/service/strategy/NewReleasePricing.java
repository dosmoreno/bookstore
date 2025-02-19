package com.example.bookstore.service.strategy;

import com.example.bookstore.entity.Book;

public class NewReleasePricing implements PricingStrategy {
    @Override
    public double calculatePrice(Book book, int quantity) {
        return book.getPrice() * quantity; // No discount
    }
}
