package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJdbcTest
@Import(BookRepositoryImpl.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldSaveNewBook() {
        Book book = new Book(null, "New Book", 1L, 1L);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3);
        assertThat(books).extracting(Book::getTitle).contains("New Book");
    }

    @Test
    void shouldUpdateExistingBook() {
        Book bookToUpdate = new Book(1L, "Updated Title", 2L, 2L);
        bookRepository.save(bookToUpdate);

        List<Book> books = bookRepository.findAll();
        Book updated = books.stream()
                .filter(b -> b.getId().equals(1L))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Book with id 1 not found"));

        assertThat(updated.getTitle()).isEqualTo("Updated Title");
        assertThat(updated.getAuthorId()).isEqualTo(2L);
        assertThat(updated.getGenreId()).isEqualTo(2L);
    }

    @Test
    void shouldDeleteBookById() {
        bookRepository.deleteById(1L);

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
        assertThat(books.stream().noneMatch(b -> b.getId().equals(1L))).isTrue();
    }

    @Test
    void deleteById_WithNonExistingId_ShouldNotFail() {
        bookRepository.deleteById(999L);

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);
    }

    @Test
    void save_WithNullBook_ShouldThrowException() {
        assertThatThrownBy(() -> bookRepository.save(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findAll_ShouldReturnAllBooks() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);
    }

    @Test
    void getById_WithExistingId_ShouldReturnBook() {
        Book book = bookRepository.getById(1L);
        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("War and Peace");
    }

    @Test
    void getById_WithNonExistingId_ShouldReturnNull() {
        Book book = bookRepository.getById(999L);
        assertThat(book).isNull();
    }
}