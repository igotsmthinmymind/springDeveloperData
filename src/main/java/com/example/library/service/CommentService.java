package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Comment;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    public CommentService(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional
    public Comment createComment(Long bookId, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        Comment comment = new Comment(null, text, book);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, String text) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}