package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void createBook(String title, Long authorId, Long genreId) {
        bookRepository.save(new Book(null, title, authorId, genreId));
    }

    public void updateBook(Long id, String title, Long authorId, Long genreId) {
        bookRepository.save(new Book(id, title, authorId, genreId));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}