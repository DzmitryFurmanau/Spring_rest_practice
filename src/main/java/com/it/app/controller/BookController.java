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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookResponseDto>> getAll() {
        final List<Book> books = bookService.findAll();
        final List<BookResponseDto> bookResponseDtoList = books.stream()
                .map((book) -> mapper.map(book, BookResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookResponseDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<BookResponseDto> getOne(@PathVariable Long id) {
        final BookResponseDto bookResponseDto = mapper.map(bookService.findById(id), BookResponseDto.class);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BookResponseDto> save(@Valid @RequestBody BookRequestDto bookRequestDto) {
        bookRequestDto.setId(null);
        final BookResponseDto bookResponseDto = mapper.map(bookService.save(getUser(bookRequestDto)), BookResponseDto.class);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BookResponseDto> update(@Valid @RequestBody BookRequestDto bookRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, bookRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.book.unexpectedId", new Object[]{}));
        }
        final BookResponseDto bookResponseDto = mapper.map(bookService.update(getUser(bookRequestDto)), BookResponseDto.class);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    private Book getUser(BookRequestDto bookRequestDto) {
        final Book book = mapper.map(bookRequestDto, Book.class);
        final Author author = new Author();
        author.setId(bookRequestDto.getAuthorId());
        book.setAuthor(author);
        return book;
    }
}
