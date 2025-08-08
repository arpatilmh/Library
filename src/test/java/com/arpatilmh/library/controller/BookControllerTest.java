package com.arpatilmh.library.controller;

import com.arpatilmh.library.model.Book;
import com.arpatilmh.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    private Book book;
    private UUID bookId;

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();
        book = new Book(bookId, "Title", "Author", "ISBN", 2020);
    }

    @Test
    void getAllBooks_returnsList() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(bookId.toString()))
                .andExpect(jsonPath("$[0].title").value("Title"));
    }

    @Test
    void getBookById_found() throws Exception {
        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId.toString()))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    void getBookById_notFound() throws Exception {
        when(bookService.getBookById(bookId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook_returnsCreated() throws Exception {
        Book toCreate = new Book(null, "Title", "Author", "1234567891", 2020);
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        String json = """
            {
                "title": "Title",
                "author": "Author",
                "isbn": "1234567891",
                "year": 2020
            }
            """;

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(bookId.toString()))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    void updateBook_found_returnsOk() throws Exception {
        Book updated = new Book(bookId, "New Title", "New Author", "1234567891", 2021);
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(Optional.of(updated));

        String json = """
            {
                "title": "New Title",
                "author": "New Author",
                "isbn": "1234567891",
                "year": 2021
            }
            """;

        mockMvc.perform(put("/api/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"));
    }

    @Test
    void updateBook_notFound_returnsNotFound() throws Exception {
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(Optional.empty());

        String json = """
            {
                "title": "New Title",
                "author": "New Author",
                "isbn": "1234567891",
                "year": 2021
            }
            """;

        mockMvc.perform(put("/api/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBook_found_returnsNoContent() throws Exception {
        when(bookService.deleteBook(bookId)).thenReturn(true);

        mockMvc.perform(delete("/api/books/{id}", bookId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBook_notFound_returnsNotFound() throws Exception {
        when(bookService.deleteBook(bookId)).thenReturn(false);

        mockMvc.perform(delete("/api/books/{id}", bookId))
                .andExpect(status().isNotFound());
    }
}
