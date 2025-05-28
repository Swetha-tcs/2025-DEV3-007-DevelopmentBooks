package com.development.service;

import com.development.softwarebooks.SoftwareBooksApplication;
import com.development.softwarebooks.config.DiscountProperties;
import com.development.softwarebooks.config.DiscountProperties.Titles;
import com.development.softwarebooks.domain.Book;
import com.development.softwarebooks.service.DiscountCalculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SoftwareBooksApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PricingServiceTest {

    @Autowired
    private DiscountCalculator calculator;

    @Autowired
    private DiscountProperties discountProperties;

    private Titles titles;

    @BeforeAll
    void setUp() {
        titles = discountProperties.getTitles();
    }

    @ParameterizedTest
    @MethodSource("bookPricingTestCases")
    void testBookPricing(List<Book> books, double expectedTotal) {
        double total = calculator.calculatePrice(books);
        assertEquals(expectedTotal, total, 0.001);
    }

    private Stream<Arguments> bookPricingTestCases() {
        return Stream.of(
            Arguments.of(List.of(book(titles.getCleanCode())), 50.0),
            Arguments.of(List.of(book(titles.getCleanCode()), book(titles.getCleanCoder())), 95.0),
            Arguments.of(List.of(book(titles.getCleanCode()), book(titles.getCleanCoder()), book(titles.getCleanArchitecture())), 135.0),
            Arguments.of(List.of(book(titles.getCleanCode()), book(titles.getCleanCoder()), book(titles.getCleanArchitecture()), book(titles.getTdd())), 160.0),
            Arguments.of(List.of(book(titles.getCleanCode()), book(titles.getCleanCoder()), book(titles.getCleanArchitecture()), book(titles.getTdd()), book(titles.getLegacyCode())), 187.50),
            Arguments.of(List.of(), 0.0),
            Arguments.of(List.of(book(titles.getCleanCode()), book(titles.getCleanCode())), 100.0),
            Arguments.of(List.of(book(titles.getCleanCode()), book(titles.getCleanCode()), book(titles.getCleanCoder())), 145.0),
            Arguments.of(List.of(book(titles.getCleanCode()), book(titles.getCleanCode()), book(titles.getCleanCoder()), book(titles.getCleanCoder()),
                                 book(titles.getCleanArchitecture()), book(titles.getTdd()), book(titles.getLegacyCode())), 282.5),
            Arguments.of(List.of(book(titles.getCleanCode()), book(titles.getCleanCode()), book(titles.getCleanCoder()), book(titles.getCleanCoder()),
                                 book(titles.getCleanArchitecture()), book(titles.getCleanArchitecture())), 270.0)
        );
    }

    @Test
    void testDuplicateBooks_OptimalGrouping_4Plus4() {
        List<Book> books = List.of(
            book(titles.getCleanCode()), book(titles.getCleanCode()),
            book(titles.getCleanCoder()), book(titles.getCleanCoder()),
            book(titles.getCleanArchitecture()), book(titles.getCleanArchitecture()),
            book(titles.getTdd()), book(titles.getLegacyCode())
        );
        double total = calculator.calculatePrice(books);
        assertEquals(320.0, total, 0.001);
    }

    @Test
    void testTenBooks_TwoEachOfFiveTitles() {
        List<Book> books = List.of(
            book(titles.getCleanCode()), book(titles.getCleanCode()),
            book(titles.getCleanCoder()), book(titles.getCleanCoder()),
            book(titles.getCleanArchitecture()), book(titles.getCleanArchitecture()),
            book(titles.getTdd()), book(titles.getTdd()),
            book(titles.getLegacyCode()), book(titles.getLegacyCode())
        );
        double total = calculator.calculatePrice(books);
        assertEquals(375.0, total, 0.001);
    }

    @Test
    void testAllSameBooks_NoDiscount() {
        List<Book> books = List.of(
            book(titles.getCleanCode()), book(titles.getCleanCode()), book(titles.getCleanCode())
        );
        double total = calculator.calculatePrice(books);
        assertEquals(150.0, total, 0.001);
    }

    @Test
    void testWorstGroupingShouldNotOccur() {
        List<Book> books = List.of(
            book(titles.getCleanCode()), book(titles.getCleanCode()),
            book(titles.getCleanCoder()), book(titles.getCleanCoder()),
            book(titles.getCleanArchitecture()), book(titles.getTdd()), book(titles.getLegacyCode())
        );
        double total = calculator.calculatePrice(books);
        assertEquals(282.5, total, 0.001);
    }

    private Book book(String title) {
        return new Book(title);
    }
}