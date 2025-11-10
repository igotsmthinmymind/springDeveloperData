package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Comment;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
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

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }
}