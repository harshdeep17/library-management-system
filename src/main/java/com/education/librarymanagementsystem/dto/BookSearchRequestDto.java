package com.education.librarymanagementsystem.dto;

import com.education.librarymanagementsystem.deserializer.BookGenreDeserializer;
import com.education.librarymanagementsystem.deserializer.BookStatusDeserializer;
import com.education.librarymanagementsystem.model.BookGenre;
import com.education.librarymanagementsystem.model.BookStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BookSearchRequestDto {
    private String query;
    @JsonDeserialize(using = BookGenreDeserializer.class)
    private BookGenre genre;
    @JsonDeserialize(using = BookStatusDeserializer.class)
    private BookStatus status;
    private int page = 0;             // Default page = 0
    private int size = 10;            // Default page size = 10
}
