package com.development.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.development.softwarebooks.domain.Book;
import com.development.softwarebooks.service.DiscountCalculator;

public class PricingServiceTest {

	@Test
	void testSingleBook_NoDiscount() {
		DiscountCalculator calculator = new DiscountCalculator();
		List<Book> books = List.of(new Book("A"));
		double total = calculator.calculateTotal(books);
		Assertions.assertEquals(50.0, total);
	}

	@Test
	void testTwoDifferentBooks_5PercentDiscount() {
	    DiscountCalculator calculator = new DiscountCalculator();
	    List<Book> books = List.of(new Book("A"), new Book("B"));
	    double total = calculator.calculateTotal(books);
	    Assertions.assertEquals(95.0, total);
	}

}
