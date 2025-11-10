package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Book> findAll() {
        return jdbcTemplate.query(
                "SELECT id, title, author_id, genre_id FROM books",
                (rs, rowNum) -> new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getLong("author_id"),
                        rs.getLong("genre_id")
                )
        );
    }

    public void save(Book book) {
        if (book.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("title", book.getTitle())
                    .addValue("authorId", book.getAuthorId())
                    .addValue("genreId", book.getGenreId());

            namedParameterJdbcTemplate.update(
                    "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :authorId, :genreId)",
                    parameters,
                    keyHolder,
                    new String[]{"id"}
            );
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE books SET title = :title, author_id = :authorId, genre_id = :genreId WHERE id = :id",
                    Map.of(
                            "id", book.getId(),
                            "title", book.getTitle(),
                            "authorId", book.getAuthorId(),
                            "genreId", book.getGenreId()
                    )
            );
        }
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = :id", Map.of("id", id));
    }

    public Book getById(Long id) {
        if (id == null) {
            return null;
        }

        String sql = "SELECT id, title, author_id, genre_id FROM books WHERE id = :id";
        List<Book> results = jdbcTemplate.query(
                sql,
                Map.of("id", id),
                (rs, rowNum) -> new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getLong("author_id"),
                        rs.getLong("genre_id")
                )
        );

        return results.isEmpty() ? null : results.get(0);
    }
}