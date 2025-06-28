package com.education.librarymanagementsystem.controller;

import com.education.librarymanagementsystem.dto.BookRequestDto;
import com.education.librarymanagementsystem.dto.BookResponseDto;
import com.education.librarymanagementsystem.model.Book;
import com.education.librarymanagementsystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(
                bookService.getAllBooks().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto bookRequestDto){
        Book book = this.bookService.addBook(bookRequestDto)
                .orElseThrow(() -> new IllegalArgumentException("Unable to add book"));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(book));
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookResponseDto> findBookById(@PathVariable("bookId") String bookId){
        Book book = this.bookService.findBookById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return ResponseEntity.status(HttpStatus.OK).body(toDto(book));
    }

    @PostMapping("/{bookId}")
    ResponseEntity<BookResponseDto> deleteBook(@PathVariable("bookId") String bookId){
        Book book = this.bookService.remove(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return ResponseEntity.status(HttpStatus.OK).body(toDto(book));
    }

    BookResponseDto toDto(Book book){
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .genre(book.getGenre())
                .status(book.getStatus())
                .price(book.getPrice())
                .issuedAt(book.getIssuedAt())
                .dueDate(book.getDueDate())
                .issuedBy(book.getIssuedBy() != null ? book.getIssuedBy() : null)
                .build();
    }

}
