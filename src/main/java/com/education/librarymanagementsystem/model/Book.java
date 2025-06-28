package com.education.librarymanagementsystem.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Book extends BaseModel{
    private String title;
    private String description;
    private String author;
    private String publisher;
    private Double price;
    private String isbn;
    private String issuedBy;
    private BookGenre genre;
    private BookStatus status;
    private LocalDateTime issuedAt;
    private LocalDateTime dueDate;
}
