package com.education.librarymanagementsystem.controller;

import com.education.librarymanagementsystem.dto.BookCopyRequestDto;
import com.education.librarymanagementsystem.dto.BookCopyResponseDto;
import com.education.librarymanagementsystem.dto.BookIssueRequestDto;
import com.education.librarymanagementsystem.model.Book;
import com.education.librarymanagementsystem.model.BookCopy;
import com.education.librarymanagementsystem.model.BookCopyStatus;
import com.education.librarymanagementsystem.repository.BookCopyRepository;
import com.education.librarymanagementsystem.repository.BookRepository;
import com.education.librarymanagementsystem.service.BookCopyService;
import com.education.librarymanagementsystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book-copies")
public class BookCopyController {
    private BookCopyService bookCopyService;
    private BookService bookService;
    private BookCopyRepository bookCopyRepository;
    public BookCopyController(BookCopyService bookCopyService, BookService bookService, BookCopyRepository bookCopyRepository){
        this.bookCopyService = bookCopyService;
        this.bookService = bookService;
        this.bookCopyRepository = bookCopyRepository;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createCopies(@RequestBody BookCopyRequestDto bookCopyRequestDto) {
        Book book = bookService.findBookById(bookCopyRequestDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        List<BookCopy> newCopies = new ArrayList<>();
        for (int i = 0; i < bookCopyRequestDto.getCount(); i++) {
            BookCopy copy = new BookCopy();
            copy.setId(UUID.randomUUID().toString());
            copy.setBookId(book.getId());
            copy.setStatus(BookCopyStatus.AVAILABLE);
            copy.setCreatedAt(LocalDateTime.now());
            copy.setUpdatedAt(LocalDateTime.now());
            bookCopyRepository.save(copy);
            newCopies.add(copy);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newCopies);
    }

    @PostMapping("/issue")
    ResponseEntity<BookCopyResponseDto> issueBook(@RequestBody BookIssueRequestDto bookIssueRequestDto){
        BookCopy bookCopy = this.bookCopyService.issueBook(bookIssueRequestDto)
                .orElseThrow(() -> new IllegalArgumentException("Unable to issue book"));
        return ResponseEntity.status(HttpStatus.OK).body(toBookCopyResponseDto(bookCopy));
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<BookCopyResponseDto>> issuedBooksByUser(@PathVariable("userId") String userId){
        List<BookCopy> bookCopies = this.bookCopyService.issuedBooksByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).
                body(bookCopies.stream().map(bookCopy-> this.toBookCopyResponseDto(bookCopy))
                        .toList());
    }

    BookCopyResponseDto toBookCopyResponseDto(BookCopy bookCopy){
        Book book = this.bookService.findBookById(bookCopy.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookCopy.getBookId()));
        return BookCopyResponseDto.builder()
                .id(bookCopy.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .genre(book.getGenre())
                .price(book.getPrice())
                .status(bookCopy.getStatus())
                .issuedAt(bookCopy.getIssuedAt())
                .dueDate(bookCopy.getDueDate())
                .issuedBy(bookCopy.getIssuedBy() != null ? bookCopy.getIssuedBy() : null)
                .build();
    }
}
