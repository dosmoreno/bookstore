package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookType;
import com.example.bookstore.entity.Customer;
import com.example.bookstore.entity.Order;
import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CustomerRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.strategy.PricingStrategy;
import com.example.bookstore.service.strategy.PricingStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PricingStrategyFactory pricingStrategyFactory;

    @Mock
    private PricingStrategy pricingStrategy;

    @Mock
    private ModelMapper modelMapper;

    private Customer customer;
    private Book book1;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        customer = new Customer(1L, "John Doe", 5); 
        book1 = new Book(1L, "Book 1", "Author 1", 29.99, BookType.REGULAR.toString());

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));
        when(pricingStrategyFactory.getStrategy(any())).thenReturn(pricingStrategy);
        when(pricingStrategy.calculatePrice(any(), anyInt())).thenReturn(29.99); 
        when(modelMapper.map(any(), eq(OrderDTO.class))).thenReturn(new OrderDTO());

        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
    }

    @Test
    void shouldPlaceOrderSuccessfully() {
        OrderDTO orderDTO = orderService.placeOrder(1L, Arrays.asList(1L));

        assertNotNull(orderDTO);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldNotPlaceOrderWhenCustomerNotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(999L, Arrays.asList(1L));
        });

        assertEquals("Customer with ID 999 not found.", exception.getMessage());
    }

    @Test
    void shouldNotPlaceOrderWhenBookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(1L, Arrays.asList(999L));
        });

        assertEquals("Book not found: ID 999", exception.getMessage());
    }
}
