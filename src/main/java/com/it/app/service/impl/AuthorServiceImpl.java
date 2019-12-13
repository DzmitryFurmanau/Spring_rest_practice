package com.it.app.service.impl;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.model.Author;
import com.it.app.repository.AuthorRepository;
import com.it.app.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final LocalizedMessageSource localizedMessageSource;

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, LocalizedMessageSource localizedMessageSource) {
        this.authorRepository = authorRepository;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.author.notExist", new Object[]{})));
    }

    @Override
    public Author save(Author author) {
        validate(author.getId() != null, localizedMessageSource.getMessage("error.author.notHaveId", new Object[]{}));
        validate(authorRepository.existsByName(author.getName()), localizedMessageSource.getMessage("error.author.name.notUnique", new Object[]{}));
        return authorRepository.saveAndFlush(author);
    }

    @Override
    public Author update(Author author) {
        final Long id = author.getId();
        validate(id == null, localizedMessageSource.getMessage("error.author.haveId", new Object[]{}));
        final Author duplicateAuthor = authorRepository.findByName(author.getName());
        findById(id);
        final boolean isDuplicateExists = duplicateAuthor != null && !Objects.equals(duplicateAuthor.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.author.name.notUnique", new Object[]{}));
        return authorRepository.saveAndFlush(author);
    }

    @Override
    public void delete(Author author) {
        final Long id = author.getId();
        validate(id == null, localizedMessageSource.getMessage("error.author.haveId", new Object[]{}));
        findById(id);
        authorRepository.delete(author);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        authorRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
