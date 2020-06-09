package pl.edu.wit.repository;

import java.util.Collection;

public interface GenericDao<T> {

    void add(T value);

    void delete(int id);

    Collection<T> findAll();

    T findById(int id);

    void close();
}
