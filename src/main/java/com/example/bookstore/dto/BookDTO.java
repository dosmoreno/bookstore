package com.example.bookstore.dto;

import com.example.bookstore.entity.BookType;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private double price;
    private BookType type;

    // Constructors
    public BookDTO() {}

    public BookDTO(Long id, String title, String author, double price, BookType type) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.type = type;
    }
}
