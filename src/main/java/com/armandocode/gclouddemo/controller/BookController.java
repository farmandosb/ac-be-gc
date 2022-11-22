package com.armandocode.gclouddemo.controller;

import com.armandocode.gclouddemo.model.Book;
import com.armandocode.gclouddemo.repository.BookRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
class BookController {
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/")
    public String hello() {
        return "hello world!";
    }

    @PostMapping("/books")
    public Book saveBook(@RequestBody Book newBook) {
        Book savedBook = this.bookRepository.save(newBook);
        return savedBook;
    }

    @GetMapping("/books")
    public String findAllBooks() {
        Iterable<Book> books = this.bookRepository.findAll();
        return Lists.newArrayList(books).toString();
    }

    @GetMapping("/books/{author}")
    public String findByAuthor(@PathVariable String author) {
        List<Book> books = this.bookRepository.findByAuthor(author);
        return books.toString();
    }

    @GetMapping("/books/{year}")
    public String findByYearAfter(@PathVariable int year) {
        List<Book> books = this.bookRepository.findByYearGreaterThan(year);
        return books.toString();
    }

    @GetMapping("/books/{author}/{year}")
    public String findByAuthorYear(@PathVariable String author, @PathVariable int year) {
        List<Book> books = this.bookRepository.findByAuthorAndYear(author, year);
        return books.toString();
    }

    @DeleteMapping("/books")
    public void removeAllBooks() {
        this.bookRepository.deleteAll();
    }
}
