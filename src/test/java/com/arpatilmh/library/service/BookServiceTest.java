package com.arpatilmh.library.service;

import com.arpatilmh.library.model.Book;
import com.arpatilmh.library.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private IBookRepository repository;

    @InjectMocks
    private BookService service;

    private Book book;
    private UUID bookId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookId = UUID.randomUUID();
        book = new Book(bookId, "Title", "Author", "ISBN", 2020);
    }

    @Test
    void getAllBooks_returnsList() {
        List<Book> books = List.of(book);
        when(repository.findAll()).thenReturn(books);

        List<Book> result = service.getAllBooks();

        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
        verify(repository).findAll();
    }

    @Test
    void getBookById_found() {
        when(repository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> result = service.getBookById(bookId);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(repository).findById(bookId);
    }

    @Test
    void getBookById_notFound() {
        when(repository.findById(bookId)).thenReturn(Optional.empty());

        Optional<Book> result = service.getBookById(bookId);

        assertFalse(result.isPresent());
        verify(repository).findById(bookId);
    }

    @Test
    void createBook_setsIdAndSaves() {
        Book bookToCreate = new Book(null, "Title", "Author", "ISBN", 2020);
        when(repository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book created = service.createBook(bookToCreate);

        assertNotNull(created.getId());
        assertEquals("Title", created.getTitle());
        verify(repository).save(any(Book.class));
    }

    @Test
    void updateBook_found_updatesAndReturns() {
        Book updatedBook = new Book(null, "New Title", "New Author", "New ISBN", 2021);
        when(repository.findById(bookId)).thenReturn(Optional.of(book));
        when(repository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Book> result = service.updateBook(bookId, updatedBook);

        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitle());
        assertEquals("New Author", result.get().getAuthor());
        assertEquals("New ISBN", result.get().getIsbn());
        assertEquals(2021, result.get().getYear());
        verify(repository).findById(bookId);
        verify(repository).save(any(Book.class));
    }

    @Test
    void updateBook_notFound_returnsEmpty() {
        Book updatedBook = new Book(null, "New Title", "New Author", "New ISBN", 2021);
        when(repository.findById(bookId)).thenReturn(Optional.empty());

        Optional<Book> result = service.updateBook(bookId, updatedBook);

        assertFalse(result.isPresent());
        verify(repository).findById(bookId);
        verify(repository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_exists_deletesAndReturnsTrue() {
        when(repository.existsById(bookId)).thenReturn(true);

        boolean result = service.deleteBook(bookId);

        assertTrue(result);
        verify(repository).existsById(bookId);
        verify(repository).deleteById(bookId);
    }

    @Test
    void deleteBook_notExists_returnsFalse() {
        when(repository.existsById(bookId)).thenReturn(false);

        boolean result = service.deleteBook(bookId);

        assertFalse(result);
        verify(repository).existsById(bookId);
        verify(repository, never()).deleteById(bookId);
    }
}

