package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    private int quantity;

    private double discountedPrice;

    public OrderItem() {}

    public OrderItem(Book book, int quantity, double discountedPrice) {
        this.book = book;
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;
    }
}

