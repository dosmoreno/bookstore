package com.example.bookstore.controller;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookType;
import com.example.bookstore.entity.Customer;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.controller.OrderController.OrderRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private ModelMapper modelMapper;

    private Customer customer;
    private Book book;

    @BeforeEach
    void setUp() {
        
        MockitoAnnotations.openMocks(this);

        customer = new Customer(1L, "John Doe", 10);
        book = new Book(1L, "Book 1", "Author 1", 29.99, BookType.REGULAR.toString());

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalPrice(29.99);

        when(orderService.placeOrder(anyLong(), anyList())).thenReturn(orderDTO);
    }

    @Test
    void testPlaceOrder() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setBookIds(Arrays.asList(1L));

        ResponseEntity<OrderDTO> response = orderController.placeOrder(1L, orderRequest);

        assertNotNull(response.getBody());
        assertEquals(29.99, response.getBody().getTotalPrice());
    }
}
