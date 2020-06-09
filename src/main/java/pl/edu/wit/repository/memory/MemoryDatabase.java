package pl.edu.wit.repository.memory;

import pl.edu.wit.model.Author;
import pl.edu.wit.model.Book;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryDatabase {

    private Map<Type, Integer> ids;
    private Map<Integer, Author> authors;
    private Map<Integer, Book> books;

    public static MemoryDatabase instance;

    public static MemoryDatabase getInstance() {
        if (instance == null) instance = new MemoryDatabase();
        return instance;
    }

    public Map<Integer, Author> getAuthors() {
        return authors;
    }

    public Map<Integer, Book> getBooks() {
        return books;
    }

    private MemoryDatabase() {
        ids = new HashMap<>();
        ids.put(Type.AUTHOR, 0);
        ids.put(Type.BOOK, 0);
        authors = new HashMap<>();
        books = new HashMap<>();
    }


    public void insertAuthor(Author author) {
        Integer id = getAndIncrementId(Type.AUTHOR);
        author.setId(id);
        authors.put(id, author);
        author.getBooks().forEach(b -> {
            if (!containsInBooksCollection(books.values(), b)) {
                insertBook(b);
            }
            addAuthorToBook(author, b);
        });
    }

    public void removeBook(Book book) {
        books.remove(book.getId());
        authors.values().forEach(a -> a.getBooks().removeIf(b -> containsInBooksCollection(a.getBooks(), book)));
    }

    public void removeAuthor(Author author) {
        authors.remove(author.getId());
        books.values().forEach(a -> a.getAuthors().removeIf(b -> containsInAuthorCollection(a.getAuthors(), author)));
    }


    public void insertBook(Book book) {
        Integer id = getAndIncrementId(Type.BOOK);
        book.setId(id);
        books.put(id, book);
        book.getAuthors().forEach(a -> {
            if (!containsInAuthorCollection(authors.values(), a)) {
                insertAuthor(a);
            }
            addAuthorToBook(a, book);
        });
    }

    private void addAuthorToBook(Author author, Book book) {
        if (author.getBooks().stream().noneMatch(b -> b.getId().equals(book.getId()))) {
            author.getBooks().add(book);
        }

        if (book.getAuthors().stream().noneMatch(a -> a.getId().equals(author.getId()))) {
            book.addAuthor(author);
        }
    }

    private boolean containsInBooksCollection(Collection<Book> books, Book book) {
        return books.stream().anyMatch(b -> b.getId().equals(book.getId()));
    }

    private boolean containsInAuthorCollection(Collection<Author> authors, Author author) {
        return authors.stream().anyMatch(a -> a.getId().equals(author.getId()));
    }


    private Integer getAndIncrementId(Type type) {
        Integer id = ids.get(type) + 1;
        ids.put(type, id);
        return id;
    }


    private enum Type {
        AUTHOR, BOOK
    }
}
