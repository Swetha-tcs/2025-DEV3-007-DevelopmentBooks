package com.development.softwarebooks.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		// Count how many times each title appears
		Map<String, Integer> bookCounts = new HashMap<>();
		for (Book book : books) {
			bookCounts.put(book.getTitle(), bookCounts.getOrDefault(book.getTitle(), 0) + 1);
		}
		double total = 0.0;
		// While there are still books left to group
		while (!bookCounts.isEmpty()) {
			Set<String> group = new HashSet<>();

			// Build one group of unique books
			for (String title : new HashSet<>(bookCounts.keySet())) {
				group.add(title);
				int count = bookCounts.get(title);
				if (count == 1) {
					bookCounts.remove(title);
				} else {
					bookCounts.put(title, count - 1);
				}
			}

			int groupSize = group.size();
			double discount = getDiscount(groupSize);
			total += groupSize * BASE_PRICE * (1 - discount);
		}

		return total;
	}

	private double getDiscount(int uniqueCount) {
		return switch (uniqueCount) {
		case 2 -> 0.05;
		case 3 -> 0.10;
		case 4 -> 0.20;
		case 5 -> 0.25;
		default -> 0.0;
		};
	}

}