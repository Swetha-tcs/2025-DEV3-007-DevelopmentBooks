package com.development.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.development.softwarebooks.service.DiscountCalculator;

class DiscountCalculatorTest {

	@Test
	void testSingleBook_NoDiscount() {
		DiscountCalculator calculator = new DiscountCalculator();
		List<Integer> books = List.of(1);
		double total = calculator.calculatePrice(books);
		Assertions.assertEquals(8.0, total, 0.001);
	}

}
