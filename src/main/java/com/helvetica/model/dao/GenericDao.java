package com.helvetica.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {

    void create(T entity);
    T findById(int id);
    T findByUsernameAndPassword(String username, String password);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
}
