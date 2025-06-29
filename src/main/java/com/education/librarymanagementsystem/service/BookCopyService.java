package com.education.librarymanagementsystem.service;

import com.education.librarymanagementsystem.dto.BookCopyRequestDto;
import com.education.librarymanagementsystem.dto.BookIssueRequestDto;
import com.education.librarymanagementsystem.dto.BookRequestDto;
import com.education.librarymanagementsystem.model.Book;
import com.education.librarymanagementsystem.model.BookCopy;
import com.education.librarymanagementsystem.model.BookCopyStatus;
import com.education.librarymanagementsystem.model.BookLock;
import com.education.librarymanagementsystem.repository.BookCopyRepository;
import com.education.librarymanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookCopyService {
    private BookCopyRepository bookCopyRepository;
    private BookLockService bookLockService;
    public BookCopyService(BookCopyRepository bookCopyRepository, BookLockService bookLockService){
        this.bookCopyRepository = bookCopyRepository;
        this.bookLockService = bookLockService;
    }

    public Optional<BookCopy> addBookCopy(BookCopyRequestDto bookCopyRequestDto){
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(UUID.randomUUID().toString());
        bookCopy.setStatus(BookCopyStatus.AVAILABLE);
        bookCopy.setCreatedAt(LocalDateTime.now());
        bookCopy.setUpdatedAt(LocalDateTime.now());
        this.bookCopyRepository.save(bookCopy);
        return Optional.of(bookCopy);
    }

    public Optional<BookCopy> findBookCopyById(String id){
        return this.bookCopyRepository.findBookCopyById(id);
    }

    public Optional<BookCopy> remove(String id){
        return this.bookCopyRepository.remove(id);
    }

    public List<BookCopy> findAvailableCopiesByBookId(String bookId){
        return this.bookCopyRepository.findAvailableCopiesByBookId(bookId);
    }

    public boolean existsCopyWithStatus(String bookId, BookCopyStatus status) {
        return bookCopyRepository.findAvailableCopiesByBookId(bookId).stream()
                .anyMatch(copy -> copy.getStatus() == status);
    }

    public Optional<BookCopy> issueBook(BookIssueRequestDto bookIssueRequestDto){
        String userId = bookIssueRequestDto.getUserId();
        String bookId = bookIssueRequestDto.getBookId();
        List<BookCopy> availableBookCopies = this.bookCopyRepository.findAvailableCopiesByBookId(bookId);
        if(availableBookCopies.isEmpty()){
            throw new IllegalArgumentException("No available copies for book: " + bookId);
        }
        BookCopy bookCopyToIssue = availableBookCopies.get(0);
        BookLock bookCopyLock = bookLockService.getLock(bookCopyToIssue.getId());
        synchronized (bookCopyLock){
            if(!bookCopyToIssue.getStatus().equals(BookCopyStatus.AVAILABLE)){
                throw new IllegalArgumentException("BookCopy with ID " + bookCopyToIssue.getId() + " is not available for issuing.");
            }

            // Fetch user details from your user service or repository (mock below)
//            Optional<User> userOpt = userRepository.findUserById(userId);
//            if (userOpt.isEmpty()) {
//                throw new IllegalArgumentException("User with ID " + userId + " not found.");
//            }

            bookCopyToIssue.setStatus(BookCopyStatus.ISSUED);
            bookCopyToIssue.setIssuedBy(userId);
            bookCopyToIssue.setIssuedAt(LocalDateTime.now());
            bookCopyToIssue.setDueDate(LocalDateTime.now().plusDays(10));
            this.bookCopyRepository.save(bookCopyToIssue);
        }
        bookLockService.releaseLock(bookCopyToIssue.getId());
        return Optional.of(bookCopyToIssue);
    }

    public List<BookCopy> issuedBooksByUser(String userId){
        //we can check use exist or not for in memory cases.
//            Optional<User> userOpt = userRepository.findUserById(userId);
//            if (userOpt.isEmpty()) {
//                throw new IllegalArgumentException("User with ID " + userId + " not found.");
//            }
        return this.bookCopyRepository.getAllBookCopy().stream()
                .filter(b -> b.getIssuedBy() != null && b.getIssuedBy().equals(userId))
                .toList();
    }
}
