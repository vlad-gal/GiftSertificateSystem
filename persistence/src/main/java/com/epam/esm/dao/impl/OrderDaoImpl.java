package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {
    private static final String SELECT_TOP_USER_TAG =
            "SELECT COUNT(t.tagId) AS widely_tag FROM Order o JOIN o.giftCertificates g " +
                    "JOIN g.tags t JOIN o.user u WHERE u.userId = ?1 GROUP BY t.tagId ORDER BY widely_tag DESC ";
    private static final String SELECT_USER_ORDERS = "SELECT o FROM Order o WHERE o.user.userId = ?1";
    private static final String SELECT_USER_ORDER = "SELECT o FROM Order o WHERE o.user.userId = ?1 AND o.orderId =?2";
    private static final String SELECT_ORDER_GIFT_CERTIFICATES =
            "SELECT g FROM Order o JOIN o.giftCertificates g WHERE o.orderId=?1";
    private static final String SELECT_ORDER_WITH_USED_GIFT_CERTIFICATES =
            "SELECT o FROM Order o JOIN o.giftCertificates g WHERE g.id=?1";

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
        return entityManager.createQuery(SELECT_TOP_USER_TAG, Long.class).setParameter(1, userId)
                .setMaxResults(1).getSingleResult();
    }

    @Override
    public List<Order> findUserOrders(long userId) {
        return entityManager.createQuery(SELECT_USER_ORDERS, Order.class)
                .setParameter(1, userId).getResultList();
    }

    @Override
    public Optional<Order> findUserOrder(long userId, long orderId) {
        return entityManager.createQuery(SELECT_USER_ORDER, Order.class)
                .setParameter(1, userId).setParameter(2, orderId).getResultStream().findFirst();
    }

    @Override
    public List<GiftCertificate> findOrderGiftCertificates(long orderId) {
        return entityManager.createQuery(SELECT_ORDER_GIFT_CERTIFICATES, GiftCertificate.class)
                .setParameter(1, orderId).getResultList();
    }

    @Override
    public boolean checkIfCertificateUsed(long certificateId) {
        List<Order> orders = entityManager
                .createQuery(SELECT_ORDER_WITH_USED_GIFT_CERTIFICATES, Order.class)
                .setParameter(1, certificateId).getResultList();
        return !orders.isEmpty();
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