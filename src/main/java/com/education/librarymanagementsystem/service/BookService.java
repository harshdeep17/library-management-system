package com.education.librarymanagementsystem.service;

import com.education.librarymanagementsystem.dto.BookRequestDto;
import com.education.librarymanagementsystem.model.Book;
import com.education.librarymanagementsystem.model.BookStatus;
import com.education.librarymanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private BookRepository bookRepository;
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
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
        book.setStatus(BookStatus.AVAILABLE);
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
}
