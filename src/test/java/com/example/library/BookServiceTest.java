package com.example.library;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.GenreRepository;
import com.example.library.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Author author;

    private Genre genre;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author(1L, "Leo Tolstoy");
        genre = new Genre(1L, "Novel");
        book = new Book(1L, "War and Peace", author, genre);
    }

    @Test
    void getAllBooks_ShouldReturnBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
        assertEquals("War and Peace", books.get(0).getTitle());
        verify(bookRepository).findAll();
    }

    @Test
    void createBook_ShouldSaveBook() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        bookService.createBook("War and Peace", 1L, 1L);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        assertEquals("War and Peace", captor.getValue().getTitle());
        assertEquals(author, captor.getValue().getAuthor());
        assertEquals(genre, captor.getValue().getGenre());
    }

    @Test
    void updateBook_ShouldUpdateExistingBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        bookService.updateBook(1L, "Anna Karenina", 1L, 1L);

        assertEquals("Anna Karenina", book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void deleteBook_ShouldDeleteById() {
        bookService.deleteBook(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void createBook_ShouldThrow_WhenAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        assertThrows(RuntimeException.class, () -> bookService.createBook("Test", 1L, 1L));
    }

    @Test
    void updateBook_ShouldThrow_WhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookService.updateBook(1L, "Title", 1L, 1L));
    }
}