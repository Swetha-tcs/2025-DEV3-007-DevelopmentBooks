package com.development.softwarebooks.service;

import org.springframework.stereotype.Service;

@Service
public class PricingService {
	private final DiscountCalculator calculator = new DiscountCalculator();

}