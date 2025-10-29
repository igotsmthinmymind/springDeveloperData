//package com.example.library;
//
//import com.example.library.model.Book;
//import com.example.library.repository.BookRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
//import org.springframework.context.annotation.Import;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@DataJdbcTest
//@Import(BookRepository.class)
//class BookRepositoryTest {
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Test
//    void shouldSaveAndFindBook() {
//        Book book = new Book(null, "Test Book", 1L, 1L);
//        bookRepository.save(book);
//
//        List<Book> books = bookRepository.findAll();
//        assertThat(books).hasSize(3);
//    }
//}