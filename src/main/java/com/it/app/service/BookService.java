package com.it.app.service;

import com.it.app.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);

    Book save(Book book);

    Book update(Book book);

    void delete(Book book);

    void deleteById(Long id);

}
