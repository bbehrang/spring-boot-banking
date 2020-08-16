package com.app.repository;

import com.app.models.Account;

import java.util.List;

public interface Dao<T> {
    T save(T obj);
    void delete(T obj);
    void deleteAll(List<T> entities);
    void saveAll(List<T> entities);
    List<T> findAll();
    T getOne(long id);

}
