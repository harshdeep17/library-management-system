package com.education.librarymanagementsystem.service;

import com.education.librarymanagementsystem.dto.BookIssueRequestDto;
import com.education.librarymanagementsystem.dto.BookRequestDto;
import com.education.librarymanagementsystem.dto.BookSearchRequestDto;
import com.education.librarymanagementsystem.model.Book;
import com.education.librarymanagementsystem.model.BookGenre;
import com.education.librarymanagementsystem.model.BookLock;
import com.education.librarymanagementsystem.model.BookCopyStatus;
import com.education.librarymanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private BookRepository bookRepository;
    private BookCopyService bookCopyService;
    private BookLockService bookLockService;
    public BookService(BookRepository bookRepository, BookCopyService bookCopyService,
                       BookLockService bookLockService){
        this.bookRepository = bookRepository;
        this.bookCopyService = bookCopyService;
        this.bookLockService = bookLockService;
    }

    public Optional<Book> addBook(BookRequestDto bookRequestDto){
        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setTitle(bookRequestDto.getTitle());
        book.setDescription(bookRequestDto.getDescription());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublisher(bookRequestDto.getPublisher());
        book.setPrice(bookRequestDto.getPrice());
        book.setGenre(bookRequestDto.getGenre());
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        this.bookRepository.save(book);
        return Optional.of(book);
    }

    public Optional<Book> findBookById(String id){
        return this.bookRepository.findBookById(id);
    }

    public Optional<Book> remove(String id){
        return this.bookRepository.remove(id);
    }

    public List<Book> getAllBooks(){
        return this.bookRepository.getAllBooks();
    }

    public List<Book> search(BookSearchRequestDto bookSearchRequestDto){
        String query = bookSearchRequestDto.getQuery();
        BookCopyStatus status = bookSearchRequestDto.getStatus();
        BookGenre genre = bookSearchRequestDto.getGenre();

        String cleanedQuery = query != null
                ? query.replaceAll("\\p{Punct}", "").toLowerCase()
                : "";

        return this.getAllBooks().stream()
                .filter(book -> {
                    Boolean queryMatches =  (book.getTitle() != null && book.getTitle().replaceAll("\\p{Punct}", "").toLowerCase().contains(cleanedQuery)) ||
                            (book.getAuthor() != null && book.getAuthor().replaceAll("\\p{Punct}", "").toLowerCase().contains(cleanedQuery)) ||
                            (book.getIsbn() != null && book.getIsbn().replaceAll("\\p{Punct}", "").toLowerCase().contains(cleanedQuery));

                    Boolean statusMatches = status != null ? this.bookCopyService.existsCopyWithStatus(book.getId(), status) : true;
                    Boolean genreMatches = genre != null ? book.getGenre().equals(genre) : true;
                    return queryMatches && statusMatches && genreMatches;
                })
//                .sorted(Comparator.comparing(Book::getTitle)) // Optional sorting
                .skip((long) bookSearchRequestDto.getPage() * bookSearchRequestDto.getSize())
                .limit(bookSearchRequestDto.getSize())
                .toList();
    }
}
