package com.arpatilmh.library.controller;

import com.arpatilmh.library.dto.BookDTO;
import com.arpatilmh.library.model.Book;
import com.arpatilmh.library.service.IBookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final IBookService service;

    @Autowired
    public BookController(IBookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = service.getAllBooks().stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(
            @PathVariable @NotNull(message = "Book ID cannot be null") UUID id) {
        return service.getBookById(id)
                .map(book -> ResponseEntity.ok(toDTO(book)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createBook(
            @Valid @RequestBody BookDTO dto) {
        Book created = service.createBook(toEntity(dto));
        return new ResponseEntity<>(toDTO(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable @NotNull(message = "Book ID cannot be null") UUID id,
            @Valid @RequestBody BookDTO dto) {
        return service.updateBook(id, toEntity(dto))
                .map(book -> ResponseEntity.ok(toDTO(book)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable @NotNull(message = "Book ID cannot be null") UUID id) {
        return service.deleteBook(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    private BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getYear()
        );
    }

    private Book toEntity(BookDTO dto) {
        return new Book(
                dto.getId(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getIsbn(),
                dto.getYear()
        );
    }
}