package com.education.librarymanagementsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BookLock {
    private String bookId;
    private LocalDateTime lockedAt;

    public BookLock(String bookId){
        this.bookId = bookId;
        this.lockedAt = LocalDateTime.now();
    }
}
