package pl.edu.wit.controller.dto;

import pl.edu.wit.model.Publisher;

public class PublisherDto {

    private final Integer id;
    private final String name;

    public PublisherDto(Publisher publisher) {
        id = publisher.getId();
        name = publisher.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
