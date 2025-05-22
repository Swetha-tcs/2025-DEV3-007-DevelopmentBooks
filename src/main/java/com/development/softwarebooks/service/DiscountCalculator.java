package com.development.softwarebooks.service;

import java.util.List;

public class DiscountCalculator {

	private static final double BASE_PRICE = 8.0;

	/**
	 * Calculates the price for a list of books (by ID). Currently, no discounts are
	 * applied.
	 */
	public double calculatePrice(List<Integer> books) {

		return books.size() * BASE_PRICE;
	}

	
}
