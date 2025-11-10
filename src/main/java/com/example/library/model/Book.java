package com.example.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private Long id;

    private String title;

    private Long authorId;

    private Long genreId;
}