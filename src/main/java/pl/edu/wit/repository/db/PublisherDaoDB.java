package pl.edu.wit.repository.db;

import pl.edu.wit.model.Publisher;
import pl.edu.wit.repository.PublisherDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

public class PublisherDaoDB implements PublisherDao {

    final EntityManager em;

    public PublisherDaoDB() {
        em = DatabaseFactory.getInstance().createEntityManager();
    }

    @Override
    public void add(Publisher publisher) {
        em.getTransaction().begin();
        if (publisher.getId() != null) {
            em.merge(publisher);
        } else {
            em.persist(publisher);
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        final Publisher publisher = em.getReference(Publisher.class, id);

        if (publisher == null) return; // TODO : Exception

        em.getTransaction().begin();
        em.remove(publisher);
        em.getTransaction().commit();
    }

    @Override
    public Collection<Publisher> findAll() {
        final TypedQuery<Publisher> query = em.createQuery("select c from Publisher c", Publisher.class);
        return query.getResultList();
    }

    @Override
    public Publisher findById(int id) {
        return em.find(Publisher.class, id);
    }

    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
