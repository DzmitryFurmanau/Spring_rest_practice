package com.it.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{book.name.notNull}")
    @NotEmpty(message = "{book.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{book.name.size}")
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{book.count.notNull}")
    @NotEmpty(message = "{book.count.notEmpty}")
    private Integer count;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{book.genre.notNull}")
    @NotEmpty(message = "{book.genre.notEmpty}")
    @Size(min = 3, max = 50, message = "{book.genre.size}")
    private String genre;

    @OneToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "{book.author.notNull}")
    private Author author;

    public Book() {

    }

    public Book(Long id, String name, Integer count, String genre, Author author) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.genre = genre;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}