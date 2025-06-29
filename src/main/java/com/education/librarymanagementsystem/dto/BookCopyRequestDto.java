package com.education.librarymanagementsystem.dto;

import lombok.Getter;

@Getter
public class BookCopyRequestDto {
    private String bookId;
    private int count;
}
