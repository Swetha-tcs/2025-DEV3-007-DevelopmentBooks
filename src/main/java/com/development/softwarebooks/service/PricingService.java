package com.development.softwarebooks.service;

import org.springframework.stereotype.Service;

import com.development.softwarebooks.domain.Basket;

@Service
public class PricingService {
	private final DiscountCalculator calculator;

    public PricingService(DiscountCalculator calculator) {
        this.calculator = calculator;
    }
    public double calculatePrice(Basket basket) {
        if (basket == null || basket.getBooks() == null) {
            return 0.0;
        }
        return calculator.calculatePrice(basket.getBooks());
    }

}