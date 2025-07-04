package com.education.librarymanagementsystem.dto;

import com.education.librarymanagementsystem.model.BookGenre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookResponseDto {
    private String id;
    private String title;
    private String description;
    private BookGenre genre;
    private String author;
    private String publisher;
    private Double price;
    private String isbn;
}
