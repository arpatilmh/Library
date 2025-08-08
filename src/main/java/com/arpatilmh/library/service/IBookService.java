package com.arpatilmh.library.service;

import com.arpatilmh.library.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBookService {
    List<Book> getAllBooks();

    Optional<Book> getBookById(UUID id);

    Book createBook(Book book);

    Optional<Book> updateBook(UUID id, Book updatedBook);

    boolean deleteBook(UUID id);
}
