package pl.edu.wit.controller.dto;

import pl.edu.wit.model.Author;

public class AuthorDto {

    private final Integer id;
    private final String name;
    private final String lastName;

    public AuthorDto(Author author) {
        id = author.getId();
        name = author.getName();
        lastName = author.getLastName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
