package com.education.librarymanagementsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BookLock {
    private String bookCopyId;
    private LocalDateTime lockedAt;

    public BookLock(String bookCopyId){
        this.bookCopyId = bookCopyId;
        this.lockedAt = LocalDateTime.now();
    }
}
