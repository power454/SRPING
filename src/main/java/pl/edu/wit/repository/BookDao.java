package pl.edu.wit.repository;

import pl.edu.wit.model.Author;
import pl.edu.wit.model.Book;

import java.util.List;
import java.util.Set;

public interface BookDao extends GenericDao<Book> {

    void update(int id, String title, String isbn);

    void update(int id, String title, String isbn, Integer pages, String descriptions, Set<Author> authors);

    List<Book> findByAuthorName(String name, String lastName);



}
