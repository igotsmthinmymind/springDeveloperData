package com.example.library.service;

import com.example.library.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    void createBook(String title, Long authorId, Long genreId);

    void updateBook(Long id, String title, Long authorId, Long genreId);

    void deleteBook(Long id);

    Book getById(Long id);
}