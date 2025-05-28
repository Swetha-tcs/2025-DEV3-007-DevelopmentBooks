package com.development.softwarebooks.controller;

import java.util.List;
import java.util.stream.Collectors;

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
		var basket = new Basket();
		var books = bookTitles.stream().map(Book::new).collect(Collectors.toList());
		books.forEach(basket::add);

		double price = pricingService.calculatePrice(basket);
		return ResponseEntity.ok(price);
	}
}