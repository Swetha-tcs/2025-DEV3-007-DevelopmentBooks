package com.development.softwarebooks.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.development.softwarebooks.config.DiscountProperties;
import com.development.softwarebooks.domain.Book;

@Service
public class DiscountCalculator {

    private final double baseBookPrice;
    private final Map<Integer, Double> discountRates;
    private final Map<String, Double> memoizationCache = new HashMap<>();

    private static final double ZERO_TOTAL = 0.0;
    private static final double INITIAL_MIN_TOTAL = Double.MAX_VALUE;

    public DiscountCalculator(DiscountProperties properties) {
        this.baseBookPrice = properties.getPrice();
        this.discountRates = properties.getDiscounts();
    }

    public double calculateTotal(List<Book> books) {
        if (books == null || books.isEmpty()) {
            return ZERO_TOTAL;
        }

        boolean containsInvalidBook = books.stream()
            .anyMatch(book -> book == null || book.getTitle() == null || book.getTitle().trim().isEmpty());

        if (containsInvalidBook) {
            throw new IllegalArgumentException("All books must be non-null and have a valid title.");
        }

        Map<String, Integer> bookCountByTitle = new HashMap<>();
        for (Book book : books) {
            bookCountByTitle.merge(book.getTitle(), 1, Integer::sum);
        }

        return computeMinimumTotalPrice(bookCountByTitle);
    }

    private double computeMinimumTotalPrice(Map<String, Integer> bookCountByTitle) {
        boolean areAllCountsZero = bookCountByTitle.values().stream().allMatch(count -> count == 0);
        if (areAllCountsZero) {
            return ZERO_TOTAL;
        }

        String memoKey = bookCountByTitle.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> entry.getValue().toString())
            .collect(Collectors.joining(","));

        if (memoizationCache.containsKey(memoKey)) {
            return memoizationCache.get(memoKey);
        }

        double minimumTotal = INITIAL_MIN_TOTAL;
        List<String> bookTitles = new ArrayList<>(bookCountByTitle.keySet());

        for (int currentGroupSize = 1; currentGroupSize <= bookTitles.size(); currentGroupSize++) {
            for (Set<String> bookGroup : generateTitleCombinations(bookTitles, currentGroupSize)) {
                boolean allBooksAvailable = bookGroup.stream().allMatch(title -> bookCountByTitle.get(title) > 0);

                if (allBooksAvailable) {
                    Map<String, Integer> updatedBookCounts = new HashMap<>(bookCountByTitle);
                    bookGroup.forEach(title -> updatedBookCounts.put(title, updatedBookCounts.get(title) - 1));

                    double groupDiscountRate = discountRates.getOrDefault(currentGroupSize, 0.0);
                    double discountedGroupPrice = currentGroupSize * baseBookPrice * (1 - groupDiscountRate);
                    double totalPrice = discountedGroupPrice + computeMinimumTotalPrice(updatedBookCounts);

                    minimumTotal = Math.min(minimumTotal, totalPrice);
                }
            }
        }

        memoizationCache.put(memoKey, minimumTotal);
        return minimumTotal;
    }

    private List<Set<String>> generateTitleCombinations(List<String> titles, int combinationSize) {
        List<Set<String>> allCombinations = new ArrayList<>();
        backtrackCombinations(titles, 0, new LinkedHashSet<>(), allCombinations, combinationSize);
        return allCombinations;
    }

    private void backtrackCombinations(
        List<String> titles,
        int currentIndex,
        Set<String> currentCombination,
        List<Set<String>> allCombinations,
        int targetSize
    ) {
        if (currentCombination.size() == targetSize) {
            allCombinations.add(new HashSet<>(currentCombination));
            return;
        }

        for (int titleIndex = currentIndex; titleIndex < titles.size(); titleIndex++) {
            String title = titles.get(titleIndex);
            currentCombination.add(title);
            backtrackCombinations(titles, titleIndex + 1, currentCombination, allCombinations, targetSize);
            currentCombination.remove(title);
        }
    }
}