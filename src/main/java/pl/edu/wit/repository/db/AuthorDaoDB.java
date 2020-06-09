package pl.edu.wit.repository.db;

import pl.edu.wit.model.Author;
import pl.edu.wit.repository.AuthorDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorDaoDB implements AuthorDao {

    final EntityManager em;

    public AuthorDaoDB() {
        em = DatabaseFactory.getInstance().createEntityManager();
    }

    @Override
    public void add(Author author) {

        // otwarcie transkacji
        em.getTransaction().begin();

        // dodanie do enitymanagera
        em.persist(author);

        // zapis do bazy - koniec transkacji
        em.getTransaction().commit();

        // insert into authors (id, last_name, name) values (null, ?, ?)
    }

    @Override
    public void update(int id, String name, String lastName) {

        final Author author = findById(id);
        if (author == null) return; // TODO: Exception

        em.getTransaction().begin();
        author.setName(name);
        author.setLastName(lastName);
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
    public List<Author> findAll() {
        // pobranie wartosci za pomoca name query
        // select author0_.id as id1_0_, author0_.last_name as last_nam2_0_, author0_.name as name3_0_ from authors author0_
        final TypedQuery<Author> query = em.createNamedQuery(Author.QUERY_FIND_ALL, Author.class);
        return query.getResultList();
    }

    // select * from authors where id = 1
    @Override
    public Author findById(int id) {
        return em.find(Author.class, id);
    }

    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
