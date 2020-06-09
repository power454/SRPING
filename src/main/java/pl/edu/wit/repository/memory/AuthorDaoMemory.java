package pl.edu.wit.repository.memory;

import pl.edu.wit.model.Author;
import pl.edu.wit.model.Book;
import pl.edu.wit.repository.AuthorDao;

import java.util.Collection;

public class AuthorDaoMemory implements AuthorDao {

    private MemoryDatabase database;

    public AuthorDaoMemory() {
        database = MemoryDatabase.getInstance();
    }


      @Override
    public void add(Author author) {
        database.insertAuthor(author);
    }

    @Override
    public void update(int id, String name, String lastName) {
        final Author author = database.getAuthors().get(id);
        if (author == null) return; // TODO: Exception
        author.setName(name);
        author.setLastName(lastName);
    }

    @Override
    public void delete(int id) {
        final Author author = findById(id);
        if (author == null) return; // TODO: Exception
        database.removeAuthor(author);
    }

    @Override
    public Collection<Author> findAll() {
        return database.getAuthors().values();
    }

    @Override
    public Author findById(int id) {
        return database.getAuthors().get(id);
    }

    @Override
    public void close() {
        // no needed
    }
}
