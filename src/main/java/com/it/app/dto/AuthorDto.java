package com.it.app.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthorDto {

    private Long id;

    @NotNull(message = "{author.name.notNull}")
    @NotEmpty(message = "{author.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{author.name.size}")
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{author.age.notNull}")
    @NotEmpty(message = "{author.age.notEmpty}")
    private Integer age;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{author.country.notNull}")
    @NotEmpty(message = "{author.country.notEmpty}")
    @Size(min = 3, max = 50, message = "{author.country.size}")
    private String country;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
