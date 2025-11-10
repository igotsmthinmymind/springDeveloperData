package com.example.library.service;

import com.example.library.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> getAllComments();

    Comment createComment(Long bookId, String text);

    Comment updateComment(Long commentId, String text);

    void deleteComment(Long commentId);

    Optional<Comment> getCommentById(Long id);
}