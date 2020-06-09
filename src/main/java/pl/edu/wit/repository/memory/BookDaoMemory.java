package pl.edu.wit.repository.memory;

import pl.edu.wit.model.Author;
import pl.edu.wit.model.Book;
import pl.edu.wit.repository.BookDao;

import java.util.*;
import java.util.stream.Collectors;

public class BookDaoMemory implements BookDao {
    private MemoryDatabase database;

    public BookDaoMemory() {
        database = MemoryDatabase.getInstance();
    }

    @Override
    public void add(Book book) {
        database.insertBook(book);
    }

    @Override
    public void update(int id, String title, String isbn) {

    }

    @Override
    public void update(int id, String title, String isbn, Integer pages, String descriptions, Set<Author> authors) {
        final Book book = findById(id);
        if (book == null) return; // TODO: Exception
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPages(pages);
        book.setDescription(descriptions);
        book.setAuthors(authors);
    }

    @Override
    public void delete(int id) {
        final Book book = findById(id);
        if (book == null) return; // TODO: Exception
        database.removeBook(book);
    }

    @Override
    public Collection<Book> findAll() {
        return database.getBooks().values();
    }

    @Override
    public Book findById(int id) {
        return database.getBooks().get(id);
    }

    @Override
    public List<Book> findByAuthorName(String name, String lastName) {

        // NOWY SPOSOB
        return database.getBooks().values().stream()
                .filter(book -> book.getAuthors().stream().anyMatch(author -> author.getName().equalsIgnoreCase(name) && author.getLastName().equalsIgnoreCase(lastName)))
                .collect(Collectors.toList());

//        STARY SPOSOB
//        final Collection<Book> books = database.getBooks().values();
//        List<Book> result = new ArrayList<>();
//        for(Book book : books) {
//            final Set<Author> authors = book.getAuthors();
//            for (Author author : authors) {
//                if (author.getName().equalsIgnoreCase(name) && author.getLastName().equalsIgnoreCase(lastName)) {
//                    result.add(book);
//                }
//            }
//        }
//        return result;
    }

    @Override
    public void close() {

    }
}
