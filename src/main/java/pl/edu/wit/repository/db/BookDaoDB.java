package pl.edu.wit.repository.db;

import org.springframework.stereotype.Repository;
import pl.edu.wit.model.Author;
import pl.edu.wit.model.Book;
import pl.edu.wit.repository.BookDao;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public class BookDaoDB implements BookDao {

    EntityManager em;

    public BookDaoDB() {
        em = DatabaseFactory.getInstance().createEntityManager();
    }

    @Override
    public void add(Book book) {
        em.getTransaction().begin();

        if (book.getId() != null) {
            em.merge(book);
        } else {
            em.persist(book);
        }
        em.getTransaction().commit();
    }

    @Override
    public void update(int id, String title, String isbn, Integer pages, String descriptions, Set<Author> authors) {
        final Book book = findById(id);
        if (book == null) return; // TODO: Exception

        em.getTransaction().begin();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPages(pages);
        book.setDescription(descriptions);
        book.setAuthors(authors);
        em.getTransaction().commit();

    }

    @Override
    public void delete(int id) {
        final Author author = em.getReference(Author.class, id);

        if (author == null) return; // TODO : Exception

        em.getTransaction().begin();
        em.remove(author);
        em.getTransaction().commit();
    }

    @Override
    public Collection<Book> findAll() {

        return em.createQuery("select b from Book b ", Book.class)
                .getResultList();
    }

    @Override
    public List<Book> findByAuthorName(String name, String lastName) {
        return em.createQuery("select b from Book b join b.authors a where a.name = :nameParam and a.lastName = :lastNameParam", Book.class)
                .setParameter("nameParam", name)
                .setParameter("lastNameParam", lastName)
                .getResultList();
    }

    @Override
    public void update(int id, String title, String isbn) {
        final Book book = findById(id);
        if (book == null) return; // TODO: Exception

        em.getTransaction().begin();
        book.setTitle(title);
        book.setIsbn(isbn);
        em.getTransaction().commit();

    }

    @Override
    public Book findById(int id) {
        return em.find(Book.class, id);
    }


    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
