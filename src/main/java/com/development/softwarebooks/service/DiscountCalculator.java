package com.development.softwarebooks.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        
        Set<String> uniqueTitles = new HashSet<>();
	    for (Book book : books) {
	        uniqueTitles.add(book.getTitle());
	    }

	    if (uniqueTitles.size() == 2 && books.size() == 2) {
	        // 2 different books → 5% discount
	        return 2 * 50.0 * 0.95;
	    }
        return books.size() * BASE_PRICE;
    }
	
	
	

}