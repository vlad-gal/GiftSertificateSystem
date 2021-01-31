package com.epam.esm.dao.impl;

import com.epam.esm.dao.JPQLQuery;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.util.QueryManager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private static final String PAGE = "page";
    private static final String PER_PAGE = "per_page";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAllByParameters(Map<String, String> queryParameters) {
        int page = Integer.parseInt(queryParameters.get(PAGE));
        int perPage = Integer.parseInt(queryParameters.get(PER_PAGE));
        int firstResult = page == 1 ? 0 : page * perPage - 2;
        String query = QueryManager.createQueryForUsers(queryParameters);
        return entityManager.createQuery(JPQLQuery.SELECT_ALL_USERS + query, User.class)
                .setFirstResult(firstResult)
                .setMaxResults(perPage)
                .getResultList();
    }

    @Override
    public Optional<User> findUserWithHighestCostOfAllOrders() {
        User foundUser = entityManager.createQuery(JPQLQuery.SELECT_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS, User.class)
                .setMaxResults(1).getSingleResult();
        return Optional.ofNullable(foundUser);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public long add(User entity) {
        throw new UnsupportedOperationException("Add is not available action for User");
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException("Remove is not available action for User");
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException("Update is not available action for User");
    }
}