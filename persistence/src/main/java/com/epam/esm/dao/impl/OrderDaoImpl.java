package com.epam.esm.dao.impl;

import com.epam.esm.dao.JPQLQuery;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> findAllByParameters(Map<String, String> queryParameters) {
        throw new UnsupportedOperationException("Find all orders by parameters is not available action for Order");
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public long findMostWidelyUsedTag(long userId) {
        return entityManager.createQuery(JPQLQuery.SELECT_TOP_USER_TAG, Long.class).setParameter(1, userId)
                .setMaxResults(1).getSingleResult();
    }

    @Override
    public List<Order> findUserOrders(long userId) {
        return entityManager.createQuery(JPQLQuery.SELECT_USER_ORDERS, Order.class)
                .setParameter(1, userId).getResultList();
    }

    @Override
    public Optional<Order> findUserOrder(long userId, long orderId) {
        return Optional.ofNullable(entityManager.createQuery(JPQLQuery.SELECT_USER_ORDER, Order.class)
                .setParameter(1, userId).setParameter(2, orderId).getSingleResult());
    }

    @Override
    public long add(Order entity) {
        entityManager.persist(entity);
        return entity.getOrderId();
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException("Remove by id is not available action for Order");
    }

    @Override
    public Order update(Order entity) {
        throw new UnsupportedOperationException("Update is not available action for Order");
    }
}