package com.arpatilmh.library.repository;

import com.arpatilmh.library.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBookRepository {
    List<Book> findAll();

    Optional<Book> findById(UUID id);

    Book save(Book book);

    void deleteById(UUID id) ;

    boolean existsById(UUID id);

}
