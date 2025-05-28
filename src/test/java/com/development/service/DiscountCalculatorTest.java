package com.development.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.development.softwarebooks.domain.Book;
import com.development.softwarebooks.service.DiscountCalculator;

public class DiscountCalculatorTest {

	private static final double DELTA = 0.01;
	private static final String CLEAN_CODE = "Clean Code";
	private static final String THE_CLEAN_CODER = "The Clean Coder";
	private static final String CODE_ARCHITECTURE = "Clean Architecture";
	private static final String TEST_DRIVEN_DEVELOPMENT = "Test Driven Development by Example";
	private static final String LEGACY_CODE = "Working Effectively with Legacy Code";

	

		@Test
		void testSingleBook_NoDiscount() {
			DiscountCalculator calculator = new DiscountCalculator();
			List<Book> books = List.of(new Book(CLEAN_CODE));
			double total = calculator.calculatePrice(books);
			assertEquals(50.0, total, DELTA);
		}

		@Test
		void testTwoDifferentBooks_5PercentDiscount() {
		    DiscountCalculator calculator = new DiscountCalculator();
		    List<Book> books = List.of(new Book(CLEAN_CODE), new Book(THE_CLEAN_CODER));
		    double total = calculator.calculatePrice(books);
		    assertEquals(95.0, total);
		}
		@Test
		void testThreeDifferentBooks_10PercentDiscount() {
		    DiscountCalculator calculator = new DiscountCalculator();
		    List<Book> books = List.of(new Book(CLEAN_CODE), new Book(THE_CLEAN_CODER), new Book(CODE_ARCHITECTURE));
		    double total = calculator.calculatePrice(books);
		    assertEquals(135.0, total);
		}
	}
