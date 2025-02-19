package com.example.bookstore.service.strategy;

import com.example.bookstore.entity.Book;

public interface PricingStrategy {
    double calculatePrice(Book book, int quantity);
}
