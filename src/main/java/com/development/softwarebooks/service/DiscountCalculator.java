package com.development.softwarebooks.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.development.softwarebooks.domain.Book;

public class DiscountCalculator {

    private static final double PRICE = 50.0;
    private static final Map<Integer, Double> DISCOUNTS = new HashMap<>();

    static {
        DISCOUNTS.put(1, 0.0);
        DISCOUNTS.put(2, 0.05);
        DISCOUNTS.put(3, 0.10);
        DISCOUNTS.put(4, 0.20);
        DISCOUNTS.put(5, 0.25);
    }

    private final Map<String, Double> memo = new HashMap<>();

    public double calculateTotal(List<Book> books) {
        if (books == null || books.isEmpty()) return 0.0;

        boolean hasInvalid = books.stream()
                .anyMatch(b -> b == null || b.getTitle() == null || b.getTitle().trim().isEmpty());

        if (hasInvalid) {
            throw new IllegalArgumentException("All books must be non-null and have a valid title.");
        }

        Map<String, Integer> count = books.stream()
                .collect(Collectors.toMap(Book::getTitle, b -> 1, Integer::sum));

        return minPrice(count);
    }

    private double minPrice(Map<String, Integer> count) {
        if (count.values().stream().allMatch(v -> v == 0)) return 0.0;

        String key = count.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getValue().toString())
                .collect(Collectors.joining(","));

        if (memo.containsKey(key)) return memo.get(key);

        double min = Double.MAX_VALUE;
        List<String> titles = new ArrayList<>(count.keySet());

        for (int size = 1; size <= titles.size(); size++) {
            for (Set<String> group : combinations(titles, size)) {
                if (group.stream().allMatch(t -> count.get(t) > 0)) {
                    Map<String, Integer> next = new HashMap<>(count);
                    group.forEach(t -> next.put(t, next.get(t) - 1));
                    double groupTotal = size * PRICE * (1 - DISCOUNTS.get(size));
                    double total = groupTotal + minPrice(next);
                    min = Math.min(min, total);
                }
            }
        }

        memo.put(key, min);
        return min;
    }

    private List<Set<String>> combinations(List<String> titles, int size) {
        List<Set<String>> result = new ArrayList<>();
        backtrack(titles, 0, new LinkedHashSet<>(), result, size);
        return result;
    }

    private void backtrack(List<String> titles, int start, Set<String> current, List<Set<String>> result, int size) {
        if (current.size() == size) {
            result.add(new HashSet<>(current));
            return;
        }
        for (int i = start; i < titles.size(); i++) {
            current.add(titles.get(i));
            backtrack(titles, i + 1, current, result, size);
            current.remove(titles.get(i));
        }
    }
}