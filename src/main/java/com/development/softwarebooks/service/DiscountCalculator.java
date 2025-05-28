package com.development.softwarebooks.service;

import java.util.List;

import com.development.softwarebooks.domain.Book;

public class DiscountCalculator {

	private static final double BASE_PRICE = 8.0;

	public double calculatePrice(List<Book> books) {

		return books.size() * BASE_PRICE;
	}

}