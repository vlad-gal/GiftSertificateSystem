package com.epam.esm.dao;

import java.util.Optional;

public interface BaseDao<T> {
    Optional<T> findById(long id);

    T add(T entity);

    void removeById(long id);

    T update(T entity);
}