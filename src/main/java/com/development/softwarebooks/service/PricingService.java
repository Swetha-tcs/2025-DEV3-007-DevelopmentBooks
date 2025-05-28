package com.development.softwarebooks.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.development.softwarebooks.domain.Basket;

@Service
public class PricingService {

    private final DiscountCalculator calculator;

    public PricingService(DiscountCalculator calculator) {
        this.calculator = calculator;
    }

    public double calculatePrice(Basket basket) {
        return Optional.ofNullable(basket)
                .map(Basket::getBooks)
                .map(calculator::calculatePrice)
                .orElse(0.0);
    }
}