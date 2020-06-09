package pl.edu.wit.repository.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseFactory {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryPU");

    private static DatabaseFactory instance;

    public static DatabaseFactory getInstance() {
        if (instance == null) instance = new DatabaseFactory();
        return instance;
    }

    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
