package com.it.app.controller;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.dto.request.BookRequestDto;
import com.it.app.dto.response.BookResponseDto;
import com.it.app.model.Author;
import com.it.app.model.Book;
import com.it.app.service.BookService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    private final Mapper mapper;

    private final BookService bookService;

    private final LocalizedMessageSource localizedMessageSource;


    public BookController(Mapper mapper, BookService bookService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.bookService = bookService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAll() {
        final List<Book> books = bookService.findAll();
        final List<BookResponseDto> bookResponseDtoList = books.stream()
                .map((book) -> mapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookResponseDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookResponseDto> getOne(@PathVariable Long id) {
        final BookResponseDto bookResponseDto = mapper.map(bookService.findById(id), BookResponseDto.class);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> save(@RequestBody BookRequestDto bookRequestDto) {
        bookRequestDto.setId(null);
        final BookResponseDto bookResponseDto = mapper.map(bookService.save(getBook(bookRequestDto)), BookResponseDto.class);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookResponseDto> update(@RequestBody BookRequestDto bookRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, bookRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.book.unexpectedId", new Object[]{}));
        }
        final BookResponseDto bookResponseDto = mapper.map(bookService.update(getBook(bookRequestDto)), BookResponseDto.class);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    private Book getBook(BookRequestDto bookRequestDto) {
        final Book book = mapper.map(bookRequestDto, Book.class);
        final Author author = new Author();
        author.setId(bookRequestDto.getAuthorId());
        book.setAuthor(author);
        return book;
    }
}
