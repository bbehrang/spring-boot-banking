package com.app.repository;

import com.app.models.Account;

import java.util.List;

public interface Dao<T> {
    T save(T obj);
    T update(long id, T obj);
    boolean delete(T obj);
    void deleteAll(List<T> entities);
    void saveAll(List<T> entities);
    List<T> findAll();
    boolean deleteById(long id);
    T getOne(long id);

}
