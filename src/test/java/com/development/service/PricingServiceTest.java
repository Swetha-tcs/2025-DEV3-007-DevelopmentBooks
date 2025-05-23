package com.development.service;

import com.development.softwarebooks.domain.Book;
import com.development.softwarebooks.service.DiscountCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PricingServiceTest {

	private DiscountCalculator calculator;

	@BeforeEach
	void setUp() {
		calculator = new DiscountCalculator();
	}

	@Test
	void testSingleBook_NoDiscount() {
		double total = calculator.calculateTotal(List.of(book("A")));
		assertEquals(50.0, total, 0.001);
	}

	@Test
	void testTwoDifferentBooks_5PercentDiscount() {
		double total = calculator.calculateTotal(List.of(book("A"), book("B")));
		assertEquals(95.0, total, 0.001);
	}

	@Test
	void testThreeDifferentBooks_10PercentDiscount() {
		double total = calculator.calculateTotal(List.of(book("A"), book("B"), book("C")));
		assertEquals(135.0, total, 0.001);
	}

	@Test
	void testFourDifferentBooks_20PercentDiscount() {
		double total = calculator.calculateTotal(List.of(book("A"), book("B"), book("C"), book("D")));
		assertEquals(160.0, total, 0.001);
	}

	@Test
	void testFiveDifferentBooks_25PercentDiscount() {
		double total = calculator.calculateTotal(List.of(book("A"), book("B"), book("C"), book("D"), book("E")));
		assertEquals(187.50, total, 0.001);
	}

	@Test
	void testDuplicateBooks_OptimalGrouping_4Plus4() {
		List<Book> books = List.of(book("Clean Code"), book("Clean Code"), book("The Clean Coder"),
				book("The Clean Coder"), book("Clean Architecture"), book("Clean Architecture"),
				book("Test Driven Development by Example"), book("Working Effectively with Legacy Code"));
		double total = calculator.calculateTotal(books);
		assertEquals(320.0, total, 0.001);
	}

	@Test
	void testTenBooks_TwoEachOfFiveTitles() {
		List<Book> books = List.of(book("A"), book("A"), book("B"), book("B"), book("C"), book("C"), book("D"),
				book("D"), book("E"), book("E"));
		double total = calculator.calculateTotal(books);
		assertEquals(375.0, total, 0.001); // 2 sets of 5 books with 25% discount each
	}

	@Test
	void testAllSameBooks_NoDiscount() {
		List<Book> books = List.of(book("A"), book("A"), book("A"));
		double total = calculator.calculateTotal(books);
		assertEquals(150.0, total, 0.001); // No discount on same titles
	}
	
	@Test
	void testEmptyCart_TotalIsZero() {
		double total = calculator.calculateTotal(List.of());
		assertEquals(0.0, total, 0.001);
	}

	@Test
	void testTwoIdenticalBooks_NoDiscount() {
		double total = calculator.calculateTotal(List.of(book("A"), book("A")));
		assertEquals(100.0, total, 0.001); // 2 × 50 = 100
	}

	@Test
	void testThreeBooks_TwoSameOneDifferent() {
		double total = calculator.calculateTotal(List.of(book("A"), book("A"), book("B")));
		// One set of 2 (5% discount), one extra
		// (50 + 50) * 0.95 = 95
		assertEquals(145.0, total, 0.001);
	}

	@Test
	void testWorstGroupingShouldNotOccur() {
		List<Book> books = List.of(
			book("A"), book("A"), book("B"), book("B"), book("C"),
			book("D"), book("E")
		);
		double total = calculator.calculateTotal(books);
		// Expected best grouping:
		// Set of 5 (A, B, C, D, E) = 187.5
		// Set of 2 (A, B) = 95.0
		// Total = 282.5
		assertEquals(282.5, total, 0.001);
	}

	@Test
	void testSevenBooks_NonOptimalIfGreedy() {
		List<Book> books = List.of(book("A"), book("A"), book("B"), book("B"), book("C"), book("D"), book("E"));
		double total = calculator.calculateTotal(books);
		// Best grouping: (A, B, C, D, E) => 187.5 + (A, B) => 95.0
		// Total = 282.5
		assertEquals(282.5, total, 0.001);
	}

	@Test
	void testSixBooks_TwoSetsOfThree() {
		List<Book> books = List.of(book("A"), book("B"), book("C"), book("A"), book("B"), book("C"));
		double total = calculator.calculateTotal(books);
		// Two sets of 3 different books = 2 × (3 × 50 × 0.9) = 270.0
		assertEquals(270.0, total, 0.001);
	}

	

	private Book book(String title) {
		return new Book(title);
	}
	
	
}
