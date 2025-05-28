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
		
		@Test
		void testFourDifferentBooks_20PercentDiscount() {
		    DiscountCalculator calculator = new DiscountCalculator();
		    List<Book> books = List.of(new Book(CLEAN_CODE), new Book(THE_CLEAN_CODER), new Book(CODE_ARCHITECTURE),new Book(TEST_DRIVEN_DEVELOPMENT));

		    double total = calculator.calculatePrice(books);
		   assertEquals(160.0, total, 0.001);
		}
		
		@Test
		void testFiveDifferentBooks_25PercentDiscount() {
		    DiscountCalculator calculator = new DiscountCalculator();
		    List<Book> books = List.of(new Book(CLEAN_CODE), new Book(THE_CLEAN_CODER), new Book(CODE_ARCHITECTURE),new Book(TEST_DRIVEN_DEVELOPMENT),new Book(LEGACY_CODE));


		    double total = calculator.calculatePrice(books);
		   assertEquals(187.50, total);
		}
		@Test
		void shouldGroupBooksOptimallyAsFourAndFour() {
		    DiscountCalculator calculator = new DiscountCalculator();
		    List<Book> books = List.of(
		        new Book(CLEAN_CODE), new Book(CLEAN_CODE),
		        new Book(THE_CLEAN_CODER), new Book(THE_CLEAN_CODER),
		        new Book(CODE_ARCHITECTURE), new Book(CODE_ARCHITECTURE),
		        new Book(TEST_DRIVEN_DEVELOPMENT), new Book(LEGACY_CODE)
		    );
		    // Optimal grouping: 2 sets of 4 different books → each gets 20% discount
		    // 2 * (4 * 50 * 0.8) = 2 * 160 = 320
		    double expected = 320.0;
		    double actual = calculator.calculatePrice(books);
		    assertEquals(expected, actual, DELTA);
		}
	}
