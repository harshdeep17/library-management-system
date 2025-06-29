package com.education.librarymanagementsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class BookCopy extends BaseModel{
    private String bookId;
    private BookCopyStatus status;
    private String issuedBy;
    private LocalDateTime issuedAt;
    private LocalDateTime dueDate;
}
