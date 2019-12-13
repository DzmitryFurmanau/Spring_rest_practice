package com.it.app.repository;

import com.it.app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByName(String name);

    Book findByName(String name);
}
