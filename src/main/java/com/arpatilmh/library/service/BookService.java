package com.arpatilmh.library.service;

import com.arpatilmh.library.model.Book;
import com.arpatilmh.library.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookService implements IBookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private IBookRepository repository;

    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return repository.findAll();
    }

    public Optional<Book> getBookById(UUID id) {
        logger.info("Fetching book with ID: {}", id);
        return repository.findById(id);
    }

    public Book createBook(Book book) {
        book.setId(UUID.randomUUID());
        Book saved = repository.save(book);
        logger.info("Created new book with ID: {}", saved.getId());
        return saved;
    }

    public Optional<Book> updateBook(UUID id, Book updatedBook) {
        logger.info("Updating book with ID: {}", id);
        return repository.findById(id).map(existing -> {
            existing.setTitle(updatedBook.getTitle());
            existing.setAuthor(updatedBook.getAuthor());
            existing.setIsbn(updatedBook.getIsbn());
            existing.setYear(updatedBook.getYear());
            Book saved = repository.save(existing);
            logger.info("Updated book with ID: {}", saved.getId());
            return saved;
        });
    }

    public boolean deleteBook(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            logger.info("Deleted book with ID: {}", id);
            return true;
        }

        logger.info("Attempted to delete non-existing book with ID: {}", id);
        return false;
    }
}
