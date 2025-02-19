package com.example.bookstore.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private int loyaltyPoints;

    public CustomerDTO() {}

    public CustomerDTO(Long id, int loyaltyPoints) {
        this.id = id;
        this.loyaltyPoints = loyaltyPoints;
    }
}
