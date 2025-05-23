package com.development.softwarebooks.service;

import com.development.softwarebooks.domain.Book;

import java.util.*;
import java.util.stream.*;

public class DiscountCalculator {

    private static final double PRICE = 50.0;
    private static final Map<Integer, Double> DISCOUNTS = Map.of(1, 0.0, 2, 0.05, 3, 0.10, 4, 0.20, 5, 0.25);
    private final Map<String, Double> memo = new HashMap<>();

    public double calculateTotal(List<Book> books) {
        if (books == null || books.isEmpty()) return 0.0;

        if (books.stream().anyMatch(b -> b == null || b.getTitle() == null || b.getTitle().trim().isEmpty())) {
            throw new IllegalArgumentException("All books must be non-null and have a valid title.");
        }

        Map<String, Integer> count = books.stream()
                .collect(Collectors.groupingBy(Book::getTitle, Collectors.summingInt(b -> 1)));

        return minPrice(count);
    }

    private double minPrice(Map<String, Integer> count) {
        String key = count.keySet().stream().sorted().map(k -> count.get(k).toString()).collect(Collectors.joining(","));
        if (memo.containsKey(key)) return memo.get(key);
        if (count.values().stream().allMatch(v -> v == 0)) return 0.0;

        double[] min = { Double.MAX_VALUE };
        List<String> titles = new ArrayList<>(count.keySet());

        for (int size = 1; size <= titles.size(); size++) {
            final int groupSize = size; // <-- Final copy for use in lambda

            comb(titles, groupSize).stream()
                .filter(g -> g.stream().allMatch(t -> count.get(t) > 0))
                .forEach(g -> {
                    Map<String, Integer> next = new HashMap<>(count);
                    g.forEach(t -> next.put(t, next.get(t) - 1));
                    double total = groupSize * PRICE * (1 - DISCOUNTS.get(groupSize)) + minPrice(next);
                    min[0] = Math.min(min[0], total);
                });
        }


        memo.put(key, min[0]);
        return min[0];
    }

    private List<Set<String>> comb(List<String> titles, int size) {
        List<Set<String>> res = new ArrayList<>();
        back(titles, 0, new LinkedHashSet<>(), res, size);
        return res;
    }

    private void back(List<String> titles, int start, Set<String> curr, List<Set<String>> res, int size) {
        if (curr.size() == size) { res.add(new HashSet<>(curr)); return; }
        for (int i = start; i < titles.size(); i++) {
            curr.add(titles.get(i));
            back(titles, i + 1, curr, res, size);
            curr.remove(titles.get(i));
        }
    }
}