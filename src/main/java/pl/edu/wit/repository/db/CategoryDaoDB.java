package pl.edu.wit.repository.db;

import pl.edu.wit.model.Category;
import pl.edu.wit.repository.CategoryDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

public class CategoryDaoDB implements CategoryDao {

    final EntityManager em;

    public CategoryDaoDB() {
        em = DatabaseFactory.getInstance().createEntityManager();
    }

    @Override
    public void add(Category category) {
        em.getTransaction().begin();
        if (category.getId() != null) {
            em.merge(category);
        } else {
            em.persist(category);
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        final Category category = em.getReference(Category.class, id);

        if (category == null) return; // TODO : Exception

        em.getTransaction().begin();
        em.remove(category);
        em.getTransaction().commit();
    }

    @Override
    public Collection<Category> findAll() {
        final TypedQuery<Category> query = em.createQuery("select c from Category c", Category.class);
        return query.getResultList();
    }

    @Override
    public Category findById(int id) {
        return em.find(Category.class, id);
    }

    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
