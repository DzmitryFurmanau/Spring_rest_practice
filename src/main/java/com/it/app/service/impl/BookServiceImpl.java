package com.it.app.service.impl;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.model.Book;
import com.it.app.repository.BookRepository;
import com.it.app.service.AuthorService;
import com.it.app.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final LocalizedMessageSource localizedMessageSource;

    private final AuthorService authorService;

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, LocalizedMessageSource localizedMessageSource) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.book.notExist", new Object[]{})));
    }

    @Override
    public Book save(Book book) {
        validate(book.getId() != null, localizedMessageSource.getMessage("error.book.notHaveId", new Object[]{}));
        validate(bookRepository.existsByName(book.getName()), localizedMessageSource.getMessage("error.book.name.notUnique", new Object[]{}));
        return saveAndFlush(book);
    }

    @Override
    public Book update(Book book) {
        final Long id = book.getId();
        validate(id == null, localizedMessageSource.getMessage("error.book.haveId", new Object[]{}));
        final Book duplicateBook = bookRepository.findByName(book.getName());
        final boolean isDuplicateExists = duplicateBook != null && !Objects.equals(duplicateBook.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.book.name.notUnique", new Object[]{}));
        findById(id);
        return saveAndFlush(book);
    }


    @Override
    public void delete(Book book) {
        final Long id = book.getId();
        validate(id == null, localizedMessageSource.getMessage("error.book.haveId", new Object[]{}));
        findById(id);
        bookRepository.delete(book);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        bookRepository.deleteById(id);
    }

    private Book saveAndFlush(Book book) {
        validate(book.getAuthor() == null || book.getAuthor().getId() == null, localizedMessageSource.getMessage("error.book.author.isNull", new Object[]{}));
        book.setAuthor(authorService.findById(book.getAuthor().getId()));
        return bookRepository.saveAndFlush(book);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
