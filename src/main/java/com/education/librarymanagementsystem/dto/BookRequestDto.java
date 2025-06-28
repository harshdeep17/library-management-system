package com.education.librarymanagementsystem.dto;

import com.education.librarymanagementsystem.model.BookGenre;
import com.education.librarymanagementsystem.model.BookStatus;
import lombok.Getter;

@Getter
public class BookRequestDto {
    private String title;
    private String description;
    private BookGenre genre;
    private BookStatus status;
    private String author;
    private String publisher;
    private Double price;
    private String isbn;
}
