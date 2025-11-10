package com.example.library.shell;

import com.example.library.model.Book;
import com.example.library.service.BookServiceImpl;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class LibraryCommands {

    private final BookServiceImpl bookService;

    public LibraryCommands(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @ShellMethod
    public void getAllBooks() {
        bookService.getAllBooks().forEach(b ->
                System.out.println(b.getId() + ": " + b.getTitle())
        );
    }

    @ShellMethod
    public void createBook(
            @ShellOption String title,
            @ShellOption Long authorId,
            @ShellOption Long genreId) {
        bookService.createBook(title, authorId, genreId);
        System.out.println("Book created.");
    }

    @ShellMethod
    public void updateBook(
            @ShellOption Long id,
            @ShellOption String title,
            @ShellOption Long authorId,
            @ShellOption Long genreId) {
        bookService.updateBook(id, title, authorId, genreId);
        System.out.println("Book updated.");
    }

    @ShellMethod
    public void deleteBook(@ShellOption Long id) {
        bookService.deleteBook(id);
        System.out.println("Book deleted.");
    }

    @ShellMethod
    public void getBookById(@ShellOption Long id) {
        Book book = bookService.getById(id);
        if (book != null) {
            System.out.println(book.getId() + ": " + book.getTitle() +
                    " (Author ID: " + book.getAuthorId() +
                    ", Genre ID: " + book.getGenreId() + ")");
        } else {
            System.out.println("Book with ID " + id + " not found.");
        }
    }
}