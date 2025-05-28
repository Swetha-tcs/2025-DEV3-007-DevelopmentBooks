package com.development.softwarebooks.domain;

import java.util.ArrayList;
import java.util.List;

public class Basket {
	private final List<Book> books = new ArrayList<>();

	public void add(Book book) {
		books.add(book);
	}

	public List<Book> getBooks() {
		return books;
	}
}