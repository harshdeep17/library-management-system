package com.education.librarymanagementsystem.repository;

import com.education.librarymanagementsystem.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class BookRepository {
    List<Book> books;

    public BookRepository(){
        this.books = new CopyOnWriteArrayList<>();
//        Book book1 = new Book();
//        books.add(book1);
    }

    public void save(Book book){
        this.books.removeIf(b -> b.getId().equals(book.getId()));
        this.books.add(book);
    }

    public Optional<Book> findBookById(String id){
        return this.books.stream().filter(b -> b.getId().equals(id))
                .findFirst();
    }

    public List<Book> getAllBooks(){
        return new ArrayList<>(this.books);
    }

    public Optional<Book> remove(String id){
        Optional<Book> optionalBook = findBookById(id);
        optionalBook.ifPresent(b -> this.books.remove(b));
        return optionalBook;
    }
}
