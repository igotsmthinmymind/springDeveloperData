package com.example.library.repository;

import com.example.library.model.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();

    void save(Book book);

    void deleteById(Long id);
}