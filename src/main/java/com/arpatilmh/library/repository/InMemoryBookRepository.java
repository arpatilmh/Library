package com.arpatilmh.library.repository;

import com.arpatilmh.library.model.Book;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryBookRepository implements  IBookRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryBookRepository.class);

    private final Map<UUID, Book> books = new HashMap<>();

    @PostConstruct
    private void init() {
        logger.info("Initializing in-memory book repository with sample data");
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();

        books.put(uuid1, new Book(uuid1, "1984", "George Orwell", "1234567890", 1949));
        books.put(uuid2, new Book(uuid2, "To Kill a Mockingbird", "Harper Lee", "0987654321", 1960));
        books.put(uuid3, new Book(uuid3, "The Great Gatsby", "F. Scott Fitzgerald", "1122334455", 1925));
        logger.info("Sample data initialized");
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Optional<Book> findById(UUID id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    public void deleteById(UUID id) {
        books.remove(id);
    }

    public boolean existsById(UUID id) {
        return books.containsKey(id);
    }
}

