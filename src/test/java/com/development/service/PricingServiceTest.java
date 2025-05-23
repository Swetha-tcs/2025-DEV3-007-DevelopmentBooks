package com.development.service;

import com.development.softwarebooks.domain.Book;
import com.development.softwarebooks.service.DiscountCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PricingServiceTest {

	private DiscountCalculator calculator;

	@BeforeEach
	void setUp() {
		calculator = new DiscountCalculator();
	}

	@ParameterizedTest
	@MethodSource("bookPricingTestCases")
	void testBookPricing(List<Book> books, double expectedTotal) {
		double total = calculator.calculateTotal(books);
		assertEquals(expectedTotal, total, 0.001);
	}

	private static Stream<Arguments> bookPricingTestCases() {
		return Stream.of(Arguments.of(List.of(book("A")), 50.0), Arguments.of(List.of(book("A"), book("B")), 95.0),
				Arguments.of(List.of(book("A"), book("B"), book("C")), 135.0),
				Arguments.of(List.of(book("A"), book("B"), book("C"), book("D")), 160.0),
				Arguments.of(List.of(book("A"), book("B"), book("C"), book("D"), book("E")), 187.50),
				Arguments.of(List.of(), 0.0), Arguments.of(List.of(book("A"), book("A")), 100.0),
				Arguments.of(List.of(book("A"), book("A"), book("B")), 145.0),
				Arguments.of(List.of(book("A"), book("A"), book("B"), book("B"), book("C"), book("D"), book("E")),
						282.5),
				Arguments.of(List.of(book("A"), book("A"), book("B"), book("B"), book("C"), book("C")), 270.0));
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
		assertEquals(375.0, total, 0.001);
	}

	@Test
	void testAllSameBooks_NoDiscount() {
		List<Book> books = List.of(book("A"), book("A"), book("A"));
		double total = calculator.calculateTotal(books);
		assertEquals(150.0, total, 0.001);
	}

	@Test
	void testWorstGroupingShouldNotOccur() {
		List<Book> books = List.of(book("A"), book("A"), book("B"), book("B"), book("C"), book("D"), book("E"));
		double total = calculator.calculateTotal(books);
		assertEquals(282.5, total, 0.001);
	}

	@Test
	void testSevenBooks_NonOptimalIfGreedy() {
		List<Book> books = List.of(book("A"), book("A"), book("B"), book("B"), book("C"), book("D"), book("E"));
		double total = calculator.calculateTotal(books);
		assertEquals(282.5, total, 0.001);
	}

	private static Book book(String title) {
		return new Book(title);
	}
}