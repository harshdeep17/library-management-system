package com.education.librarymanagementsystem.controller;

import com.education.librarymanagementsystem.dto.*;
import com.education.librarymanagementsystem.model.Book;
import com.education.librarymanagementsystem.model.BookCopy;
import com.education.librarymanagementsystem.service.BookCopyService;
import com.education.librarymanagementsystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;
    private BookCopyService bookCopyService;

    public BookController(BookService bookService, BookCopyService bookCopyService){
        this.bookService = bookService;
        this.bookCopyService = bookCopyService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(
                bookService.getAllBooks().stream()
                        .map(this::toBookResponseDto)
                        .toList()
        );
    }

    @PostMapping
    ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto bookRequestDto){
        Book book = this.bookService.addBook(bookRequestDto)
                .orElseThrow(() -> new IllegalArgumentException("Unable to add book"));
        return ResponseEntity.status(HttpStatus.CREATED).body(toBookResponseDto(book));
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookResponseDto> findBookById(@PathVariable("bookId") String bookId){
        Book book = this.bookService.findBookById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return ResponseEntity.status(HttpStatus.OK).body(toBookResponseDto(book));
    }

    @PostMapping("/{bookId}")
    ResponseEntity<BookResponseDto> deleteBook(@PathVariable("bookId") String bookId){
        Book book = this.bookService.remove(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return ResponseEntity.status(HttpStatus.OK).body(toBookResponseDto(book));
    }

    @PostMapping("/search")
    ResponseEntity<List<BookResponseDto>> search(@RequestBody BookSearchRequestDto bookSearchRequestDto){
        List<Book> books = this.bookService.search(bookSearchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).
                body(books.stream().map(book-> this.toBookResponseDto(book))
                        .toList());
    }


    BookResponseDto toBookResponseDto(Book book){
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .genre(book.getGenre())
                .price(book.getPrice())
                .build();
    }


}
