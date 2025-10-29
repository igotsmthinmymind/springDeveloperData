package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void createBook(String title, Long authorId, Long genreId) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        Genre genre = genreRepository.findById(genreId).orElseThrow();
        Book book = new Book(null, title, author, genre);
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Long id, String title, Long authorId, Long genreId) {
        Book book = bookRepository.findById(id).orElseThrow();
        Author author = authorRepository.findById(authorId).orElseThrow();
        Genre genre = genreRepository.findById(genreId).orElseThrow();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}