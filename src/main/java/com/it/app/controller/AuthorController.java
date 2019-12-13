package com.it.app.controller;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.dto.AuthorDto;
import com.it.app.model.Author;
import com.it.app.service.AuthorService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final Mapper mapper;

    private final AuthorService authorService;

    private final LocalizedMessageSource localizedMessageSource;

    public AuthorController(Mapper mapper, AuthorService authorService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.authorService = authorService;
        this.localizedMessageSource = localizedMessageSource;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AuthorDto>> getAll() {
        final List<Author> authors = authorService.findAll();
        final List<AuthorDto> authorDtoList = authors.stream()
                .map((role) -> mapper.map(role, AuthorDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AuthorDto> getOne(@PathVariable Long id) {
        final AuthorDto authorDto = mapper.map(authorService.findById(id), AuthorDto.class);
        return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AuthorDto> save(@Valid @RequestBody AuthorDto authorDto) {
        authorDto.setId(null);
        final AuthorDto responseAuthorDto = mapper.map(authorService.save(mapper.map(authorDto, Author.class)), AuthorDto.class);
        return new ResponseEntity<>(responseAuthorDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<AuthorDto> update(@Valid @RequestBody AuthorDto authorDto, @PathVariable Long id) {
        if (!Objects.equals(id, authorDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.author.unexpectedId", new Object[]{}));
        }
        final AuthorDto responseAuthorDto = mapper.map(authorService.update(mapper.map(authorDto, Author.class)), AuthorDto.class);
        return new ResponseEntity<>(responseAuthorDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        authorService.deleteById(id);
    }
}
