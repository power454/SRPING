package pl.edu.wit.controller.dto;

import pl.edu.wit.model.Category;

public class CategoryDto {

    private final Integer id;
    private final String name;

    public CategoryDto(Category category) {
        id = category.getId();
        name = category.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
