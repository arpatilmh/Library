package com.arpatilmh.library.repository;

import com.arpatilmh.library.model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    private static IBookRepository repository;

    @BeforeAll
    static void setUp() {
        repository = new InMemoryBookRepository();
    }

    @BeforeEach
    void clearRepository() {
        repository.findAll().forEach(book -> repository.deleteById(book.getId()));
    }

    @Test
    @DisplayName("Save and find book by id")
    void saveAndFindById() {
        Book book = new Book(UUID.randomUUID(), "Test Title", "Test Author", "1234567890", 2022);
        Book saved = repository.save(book);

        Optional<Book> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Title", found.get().getTitle());
    }

    @Test
    @DisplayName("Find all books")
    void findAllBooks() {
        Book book1 = new Book(UUID.randomUUID(), "Title1", "Author1", "111", 2020);
        Book book2 = new Book(UUID.randomUUID(), "Title2", "Author2", "222", 2021);
        repository.save(book1);
        repository.save(book2);

        List<Book> books = repository.findAll();
        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Delete book by id")
    void deleteById() {
        Book book = new Book(UUID.randomUUID(), "Delete Title", "Delete Author", "333", 2019);
        Book saved = repository.save(book);

        repository.deleteById(saved.getId());
        Optional<Book> found = repository.findById(saved.getId());
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Exists by id")
    void existsById() {
        Book book = new Book(UUID.randomUUID(), "Exists Title", "Exists Author", "444", 2018);
        Book saved = repository.save(book);

        assertTrue(repository.existsById(saved.getId()));
        assertFalse(repository.existsById(UUID.randomUUID()));
    }
}

