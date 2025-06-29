package com.education.librarymanagementsystem.repository;

import com.education.librarymanagementsystem.model.BookCopy;
import com.education.librarymanagementsystem.model.BookCopyStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class BookCopyRepository {
    private final List<BookCopy> bookCopies = new CopyOnWriteArrayList<>();

    public BookCopyRepository(){

    }

    public void save(BookCopy bookCopy){
        this.bookCopies.removeIf(b -> b.getId().equals(bookCopy.getId()));
        bookCopies.add(bookCopy);
    }

    public Optional<BookCopy> findBookCopyById(String bookCopyId){
        return bookCopies.stream()
                .filter(b -> b.getId().equals(bookCopyId))
                .findFirst();
    }

    public List<BookCopy> getAllBookCopy(){
        return new ArrayList<>(this.bookCopies);
    }

    public List<BookCopy> findAvailableCopiesByBookId(String bookId){
        return this.bookCopies.stream()
                .filter(b -> b.getBookId().equals(bookId) &&
                        b.getStatus().equals(BookCopyStatus.AVAILABLE))
                .toList();
    }

    public Optional<BookCopy> remove(String bookCopyId){
        Optional<BookCopy> optionalBookCopy = findBookCopyById(bookCopyId);
        optionalBookCopy.ifPresent(b -> this.bookCopies.remove(b));
        return optionalBookCopy;
    }
}
