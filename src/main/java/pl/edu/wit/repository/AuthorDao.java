package pl.edu.wit.repository;

import pl.edu.wit.model.Author;

/**
 * Interface dao dla autorow
 * pozwala na dodawanie, zmiane, usuniecie jak i na znalezienie danych
 */
public interface AuthorDao extends GenericDao<Author> {

    void update(int id, String name, String lastName);

}
