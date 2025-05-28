package com.development.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.development.softwarebooks.domain.Basket;
import com.development.softwarebooks.domain.Book;
import com.development.softwarebooks.service.DiscountCalculator;
import com.development.softwarebooks.service.PricingService;

class PricingServiceTest {

    private DiscountCalculator calculator;
    private PricingService pricingService;

    @BeforeEach
    void setup() {
    	calculator = mock(DiscountCalculator.class);
        pricingService = new PricingService(calculator);
    }

    @Test
    void returnsZeroWhenBasketIsNull() {
        double price = pricingService.calculatePrice(null);
        assertEquals(0.0, price);
    }

    @Test
    void returnsZeroWhenBookListIsNull() {
        Basket basket = new Basket(); // no books added, list is empty but not null by default
        double price = pricingService.calculatePrice(basket);
        assertEquals(0.0, price);
    }

    @Test
    void delegatesCalculationToDiscountCalculator() {
        List<Book> books = List.of(new Book("Clean Code"), new Book("Clean Coder"));
        Basket basket = new Basket();
        books.forEach(basket::add);

        when(calculator.calculatePrice(books)).thenReturn(95.0);

        double price = pricingService.calculatePrice(basket);

        assertEquals(95.0, price);
        verify(calculator).calculatePrice(books);
    }
}