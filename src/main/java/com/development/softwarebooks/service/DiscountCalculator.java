package com.development.softwarebooks.service;

import java.util.List;

import com.development.softwarebooks.domain.Book;

public class DiscountCalculator {

	private static final double BASE_PRICE = 50.0;

	/**
	 * Calculates the price for a list of books. Currently, no discounts are
	 * applied.
	 */
	
	public double calculatePrice(List<Book> books) {
        if (books == null || books.isEmpty()) {
            return 0.0;
        }
        return books.size() * BASE_PRICE;
    }
}
