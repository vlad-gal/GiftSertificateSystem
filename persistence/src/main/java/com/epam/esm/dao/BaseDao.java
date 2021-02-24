package com.epam.esm.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseDao<T> {

    List<T> findAllByParameters(Map<String, String> queryParameters);

    Optional<T> findById(long id);

    long add(T entity);

    void removeById(long id);

    T update(T entity);
}