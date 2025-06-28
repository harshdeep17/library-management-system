package com.education.librarymanagementsystem.dto;

import com.education.librarymanagementsystem.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BookSearchResponseDto {
    private List<Book> books;
    private int page;
    private int size;
    private int totalElements;
}
