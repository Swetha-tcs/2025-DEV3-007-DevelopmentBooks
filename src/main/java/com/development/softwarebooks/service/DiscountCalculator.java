package com.development.softwarebooks.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.development.softwarebooks.domain.Book;

@Service
public class DiscountCalculator {
	private static final double ZERO_TOTAL = 0.0;
	private static final double BOOK_PRICE = 50.0;

	public double calculatePrice(List<Book> books) {
		if (books == null || books.isEmpty()) {
			return ZERO_TOTAL;
		}

		boolean containsInvalidBook = books.stream()
				.anyMatch(book -> book == null || book.getTitle() == null || book.getTitle().trim().isEmpty());
		if (containsInvalidBook) {
			throw new IllegalArgumentException("All books must be non-null and have a valid title.");
		}

		// Count each book title
		Map<String, Integer> bookCounts = new HashMap<>();
		for (Book book : books) {
			bookCounts.merge(book.getTitle(), 1, Integer::sum);
		}

		List<Integer> groupSizes = new ArrayList<>();

		// Create greedy groupings
		while (!bookCounts.isEmpty()) {
			Set<String> group = new HashSet<>();
			for (String title : new HashSet<>(bookCounts.keySet())) {
				group.add(title);
				bookCounts.put(title, bookCounts.get(title) - 1);
				if (bookCounts.get(title) == 0) {
					bookCounts.remove(title);
				}
			}
			groupSizes.add(group.size());
		}

		// Apply 4+4 optimization: Replace one (5, 3) with two 4s
		while (groupSizes.contains(5) && groupSizes.contains(3)) {
			groupSizes.remove(Integer.valueOf(5));
			groupSizes.remove(Integer.valueOf(3));
			groupSizes.add(4);
			groupSizes.add(4);
		}

		// Calculate total price with discounts
		double total = 0.0;
		for (int size : groupSizes) {
			double discount = getDiscount(size);
			total += size * BOOK_PRICE * (1 - discount);
		}

		return total;
	}

	private double getDiscount(int uniqueCount) {
		return switch (uniqueCount) {
		case 2 -> 0.05; // 5% discount for 2 different books
		case 3 -> 0.10; // 10% discount for 3 different books
		case 4 -> 0.20; // 20% discount for 4 different books
		case 5 -> 0.25; // 25% discount for 5 different books
		default -> 0.0; // No discount for single books or 0 books
		};
	}

}