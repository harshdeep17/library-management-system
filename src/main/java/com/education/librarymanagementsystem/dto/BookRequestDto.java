package com.education.librarymanagementsystem.dto;

import com.education.librarymanagementsystem.deserializer.BookGenreDeserializer;
import com.education.librarymanagementsystem.deserializer.BookStatusDeserializer;
import com.education.librarymanagementsystem.model.BookGenre;
import com.education.librarymanagementsystem.model.BookCopyStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

@Getter
public class BookRequestDto {
    private String title;
    private String description;
    @JsonDeserialize(using = BookGenreDeserializer.class)
    private BookGenre genre;
    private String author;
    private String publisher;
    private Double price;
    private String isbn;
}
