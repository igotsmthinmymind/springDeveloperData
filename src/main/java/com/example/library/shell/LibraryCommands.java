package com.example.library.shell;

import com.example.library.model.Book;
import com.example.library.model.Comment;
import com.example.library.service.BookServiceImpl;
import com.example.library.service.CommentServiceImpl;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class LibraryCommands {

    private final BookServiceImpl bookService;

    private final CommentServiceImpl commentService;

    public LibraryCommands(BookServiceImpl bookService, CommentServiceImpl commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
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
    public void getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        comments.forEach(c -> System.out.println(c.getId() + ": " + c.getText() +
                ", Book: " + c.getBook().getTitle()));
    }

    @ShellMethod
    public void createComment(
            @ShellOption Long bookId,
            @ShellOption String text) {
        Comment comment = commentService.createComment(bookId, text);
        System.out.println("Comment created with id: " + comment.getId());
    }

    @ShellMethod
    public void updateComment(
            @ShellOption Long commentId,
            @ShellOption String text) {
        Comment comment = commentService.updateComment(commentId, text);
        System.out.println("Comment updated with id: " + comment.getId());
    }

    @ShellMethod
    public void deleteComment(@ShellOption Long commentId) {
        commentService.deleteComment(commentId);
        System.out.println("Comment deleted with id: " + commentId);
    }

    @ShellMethod
    public void getBookById(@ShellOption Long id) {
        Optional<Book> optionalBook = bookService.getBookById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            System.out.println(book.getId() + ": " + book.getTitle() +
                    " (Author ID: " + book.getAuthor() +
                    ", Genre ID: " + book.getGenre() + ")");
        } else {
            System.out.println("Book with ID " + id + " not found.");
        }
    }

    @ShellMethod
    public void getCommentById(@ShellOption Long id) {
        Optional<Comment> optionalComment = commentService.getCommentById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            System.out.println(
                    "Comment ID: " + comment.getId() +
                            "\nText: " + comment.getText() +
                            "\nBook ID: " + comment.getBook()
            );
        } else {
            System.out.println("Comment with ID " + id + " not found.");
        }
    }
}