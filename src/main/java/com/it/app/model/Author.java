package com.it.app.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{author.name.notNull}")
    @NotEmpty(message = "{author.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{author.name.size}")
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{author.age.notNull}")
    @NotEmpty(message = "{author.age.notEmpty}")
    @Range(min = 1, max = 100, message = "{author.age.range}")
    private Integer age;

    @Column(unique = true, nullable = false)
    @NotNull(message = "{author.country.notNull}")
    @NotEmpty(message = "{author.country.notEmpty}")
    @Size(min = 3, max = 50, message = "{author.country.size}")
    private String country;

    public Author() {

    }

    public Author(Long id, String name, Integer age, String country) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.country = country;
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
