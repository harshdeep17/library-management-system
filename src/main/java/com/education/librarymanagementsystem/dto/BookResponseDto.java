package com.education.librarymanagementsystem.dto;

import com.education.librarymanagementsystem.model.BookGenre;
import com.education.librarymanagementsystem.model.BookStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookResponseDto {
    private String id;
    private String title;
    private String description;
    private BookGenre genre;
    private BookStatus status;
    private String author;
    private String publisher;
    private Double price;
    private String isbn;
    private String issuedBy;
    private LocalDateTime issuedAt;
    private LocalDateTime dueDate;
}
