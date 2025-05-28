package com.development.softwarebooks.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.development.softwarebooks.domain.Basket;
import com.development.softwarebooks.domain.Book;
import com.development.softwarebooks.service.PricingService;

@RestController
@RequestMapping("/api/price")
public class PricingController {

	private final PricingService pricingService;

	public PricingController(PricingService pricingService) {
		this.pricingService = pricingService;
	}
	@PostMapping
    public ResponseEntity<Double> calculatePrice(@RequestBody List<String> bookTitles) {
        Basket basket = new Basket();
        bookTitles.forEach(title -> basket.add(new Book(title)));
        double price = pricingService.calculatePrice(basket);
        return ResponseEntity.ok(price);
    }
}