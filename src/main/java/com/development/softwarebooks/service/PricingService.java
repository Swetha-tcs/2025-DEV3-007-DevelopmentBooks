package com.development.softwarebooks.service;

import org.springframework.stereotype.Service;

import com.development.softwarebooks.domain.Basket;

@Service
public class PricingService {
	private final DiscountCalculator calculator = new DiscountCalculator();
	public double calculatePrice(Basket basket) {
        return calculator.calculateTotal(basket.getBooks());
    }
}
