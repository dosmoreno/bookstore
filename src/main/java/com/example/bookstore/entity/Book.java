package com.example.bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private double price;
    @Enumerated(EnumType.STRING)
    private BookType type;

    public Book() {
    }

    public Book(Long id, String title, String author, double price, String type) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.type = BookType.valueOf(type.toUpperCase());
    }
        
    public Book(String title, String author, double price, String type) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.type = BookType.valueOf(type.toUpperCase());
    }
}
