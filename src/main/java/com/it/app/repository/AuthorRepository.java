package com.it.app.repository;

import com.it.app.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByName(String name);

    Author findByName(String name);
}
