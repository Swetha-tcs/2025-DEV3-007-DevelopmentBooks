package com.development.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.development.softwarebooks.SoftwareBooksApplication;
import com.development.softwarebooks.config.DiscountProperties;
import com.development.softwarebooks.config.DiscountProperties.Titles;
import com.development.softwarebooks.domain.Book;
import com.development.softwarebooks.service.DiscountCalculator;

@SpringBootTest(classes = SoftwareBooksApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DiscountCalculatorTest {

    @Autowired
    private DiscountCalculator discountCalculator;

    @Autowired
    private DiscountProperties discountProperties;

    private Titles titles;

    private static final double DELTA = 0.01;

    @BeforeAll
    void setUp() {
        titles = discountProperties.getTitles();
    }

    @Test
    @DisplayName("Should return 0.0 for null or empty book list")
    void givenNullOrEmptyList_shouldReturnZero() {
        assertEquals(0.0, discountCalculator.calculatePrice(null));
        assertEquals(0.0, discountCalculator.calculatePrice(List.of()));
    }

    @ParameterizedTest(name = "Invalid book input: {0}")
    @MethodSource("provideInvalidBooks")
    @DisplayName("Should throw exception for invalid book entries")
    void givenInvalidBooks_shouldThrowException(List<Book> books) {
        assertThrows(IllegalArgumentException.class, () -> discountCalculator.calculatePrice(books));
    }

    private Stream<Arguments> provideInvalidBooks() {
        return Stream.of(
            Arguments.of(List.of(new Book((String) null))),
            Arguments.of(List.of(new Book(""))),
            Arguments.of(List.of(new Book("   "))),
            Arguments.of(Arrays.asList(new Book(titles.getCleanCode()), null))
        );
    }

    @Test
    @DisplayName("Should return base price for a single valid book")
    void givenSingleValidBook_shouldReturnBasePrice() {
        double expected = discountProperties.getPrice();
        List<Book> books = List.of(new Book(titles.getCleanCode()));
        assertEquals(expected, discountCalculator.calculatePrice(books), DELTA);
    }

    @Test
    @DisplayName("Should not apply discount for duplicate books")
    void givenTwoSameBooks_shouldReturnNoDiscount() {
        double price = discountProperties.getPrice();
        double expected = 2 * price;
        List<Book> books = List.of(new Book(titles.getCleanCode()), new Book(titles.getCleanCode()));
        assertEquals(expected, discountCalculator.calculatePrice(books), DELTA);
    }

    @ParameterizedTest(name = "Books: {0} -> Expected total: {1}")
    @MethodSource("provideDiscountScenarios")
    @DisplayName("Should correctly calculate total for various discount scenarios")
    void givenBooksWithExpectedDiscount(List<Book> books, double expectedTotal) {
        double actualTotal = discountCalculator.calculatePrice(books);
        assertEquals(expectedTotal, actualTotal, DELTA);
    }

    private Stream<Arguments> provideDiscountScenarios() {
        double price = discountProperties.getPrice();
        Map<Integer, Double> discounts = discountProperties.getDiscounts();

        return Stream.of(
            // 3 unique books
            Arguments.of(
                List.of(new Book(titles.getCleanCode()), new Book(titles.getCleanCoder()), new Book(titles.getCleanArchitecture())),
                3 * price * (1 - discounts.getOrDefault(3, 0.0))
            ),

            // Two sets: 3 unique, 2 unique
            Arguments.of(
                List.of(
                    new Book(titles.getCleanCode()), new Book(titles.getCleanCode()),
                    new Book(titles.getCleanCoder()), new Book(titles.getCleanCoder()),
                    new Book(titles.getCleanArchitecture())
                ),
                (3 * price * (1 - discounts.getOrDefault(3, 0.0))) +
                (2 * price * (1 - discounts.getOrDefault(2, 0.0)))
            ),

            // 5 unique books
            Arguments.of(
                List.of(
                    new Book(titles.getCleanCode()), new Book(titles.getCleanCoder()), new Book(titles.getCleanArchitecture()),
                    new Book(titles.getTdd()), new Book(titles.getLegacyCode())
                ),
                5 * price * (1 - discounts.getOrDefault(5, 0.0))
            ),

            
            // 7 same books (no discount)
            Arguments.of(
                List.of(
                    new Book(titles.getCleanCode()), new Book(titles.getCleanCode()),
                    new Book(titles.getCleanCode()), new Book(titles.getCleanCode()),
                    new Book(titles.getCleanCode()), new Book(titles.getCleanCode()),
                    new Book(titles.getCleanCode())
                ),
                7 * price
            ),

            // Optimal grouping: 4 + 4 is cheaper than 5 + 3
            Arguments.of(
                List.of(
                    new Book(titles.getCleanCode()), new Book(titles.getCleanCode()),
                    new Book(titles.getCleanCoder()), new Book(titles.getCleanCoder()),
                    new Book(titles.getCleanArchitecture()), new Book(titles.getCleanArchitecture()),
                    new Book(titles.getTdd()), new Book(titles.getLegacyCode())
                ),
                2 * (4 * price * (1 - discounts.getOrDefault(4, 0.0)))
            )
        );
    }
}