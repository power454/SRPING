package pl.edu.wit.controller.dto;

import pl.edu.wit.model.Book;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDto {
    private final Integer id;
    private final String isbn;
    private final String title;
    private final String description;
    private final Set<AuthorDto> authors;
    private final Integer pages;
    private final PublisherDto publisher;
    private final CategoryDto category;
    private final BigDecimal price;

    public BookDto(Book book) {
        id = book.getId();
        isbn = book.getIsbn();
        title = book.getTitle();
        description = book.getDescription();
        pages = book.getPages();
        authors = book.getAuthors().stream().map(AuthorDto::new).collect(Collectors.toSet());
        publisher = new PublisherDto(book.getPublisher());
        category = new CategoryDto(book.getCategory());
        price = book.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<AuthorDto> getAuthors() {
        return authors;
    }

    public Integer getPages() {
        return pages;
    }

    public PublisherDto getPublisher() {
        return publisher;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
