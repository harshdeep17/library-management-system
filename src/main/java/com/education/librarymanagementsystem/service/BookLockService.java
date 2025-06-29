package com.education.librarymanagementsystem.service;

import com.education.librarymanagementsystem.model.BookLock;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BookLockService {
    Map<String, BookLock> bookLockMap = new ConcurrentHashMap<>();

    BookLockService(){

    }

    public BookLock getLock(String bookCopyId){
        BookLock bookLock = new BookLock(bookCopyId);
        bookLockMap.putIfAbsent(bookCopyId, bookLock);
        return bookLock;
    }

    public void releaseLock(String bookCopyId){
        bookLockMap.remove(bookCopyId);
    }
}
