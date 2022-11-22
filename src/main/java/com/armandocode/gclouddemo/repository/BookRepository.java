package com.armandocode.gclouddemo.repository;

import com.armandocode.gclouddemo.model.Book;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

import java.util.List;

public interface BookRepository extends DatastoreRepository<Book, Long> {

    List<Book> findByAuthor(String author);

    List<Book> findByYearGreaterThan(int year);

    List<Book> findByAuthorAndYear(String author, int year);
}
