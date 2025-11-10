package com.example.library;

import com.example.library.model.Book;
import com.example.library.model.Comment;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CommentRepository;
import com.example.library.service.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Book book;
    private Comment comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book(1L, "War and Peace", null, null);
        comment = new Comment(1L, "Great book!", book);
    }

    @Test
    void getAllComments_ShouldReturnComments() {
        when(commentRepository.findAll()).thenReturn(List.of(comment));
        List<Comment> comments = commentService.getAllComments();
        assertEquals(1, comments.size());
        assertEquals("Great book!", comments.get(0).getText());
        verify(commentRepository).findAll();
    }

    @Test
    void createComment_ShouldSaveComment() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comment created = commentService.createComment(1L, "Awesome!");
        assertEquals("Awesome!", created.getText());
        assertEquals(book, created.getBook());

        verify(bookRepository).findById(1L);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void updateComment_ShouldUpdateText() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comment updated = commentService.updateComment(1L, "Updated comment");
        assertEquals("Updated comment", updated.getText());
        verify(commentRepository).findById(1L);
        verify(commentRepository).save(comment);
    }

    @Test
    void deleteComment_ShouldCallDeleteById() {
        commentService.deleteComment(1L);
        verify(commentRepository).deleteById(1L);
    }

    @Test
    void createComment_ShouldThrow_WhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> commentService.createComment(1L, "Text"));
    }

    @Test
    void updateComment_ShouldThrow_WhenCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> commentService.updateComment(1L, "Text"));
    }
}