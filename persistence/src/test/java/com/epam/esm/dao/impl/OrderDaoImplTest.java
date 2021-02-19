package com.epam.esm.dao.impl;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PersistenceConfig.class})
@ActiveProfiles("test")
@Transactional
class OrderDaoImplTest {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Test
    void whenFindAllByParametersThenShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> orderDao.findAllByParameters(null));
    }

    @Test
    void whenFindByExistIdThenShouldReturnTrue() {
        Optional<Order> order = orderDao.findById(1);
        boolean condition = order.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByNotExistIdThenShouldReturnFalse() {
        Optional<Order> order = orderDao.findById(1878);
        boolean condition = order.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenFindMostWidelyUsedTagThenShouldReturnTagId() {
        long tagId = orderDao.findMostWidelyUsedTag(1);
        assertEquals(5, tagId);
    }

    @Test
    void whenFindUserOrdersThenShouldReturnListOfOrders() {
        List<Order> orders = orderDao.findUserOrders(1);
        assertFalse(orders.isEmpty());
    }

    @Test
    void whenFindUserOrdersThenShouldReturnEmptyList() {
        List<Order> orders = orderDao.findUserOrders(10000);
        assertTrue(orders.isEmpty());
    }

    @Test
    void whenFindUserOrderThenShouldReturnOrder() {
        Optional<Order> order = orderDao.findUserOrder(1, 1);
        boolean condition = order.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindUserOrderThenShouldReturnEmpty() {
        assertThrows(NoResultException.class, () -> orderDao.findUserOrder(1, 12));
    }

    @Test
    void whenFindOrderGiftCertificatesThenShouldReturnListOfGiftCertificates() {
        List<GiftCertificate> orderGiftCertificates = orderDao.findOrderGiftCertificates(1);
        assertFalse(orderGiftCertificates.isEmpty());
    }

    @Test
    void whenFindOrderGiftCertificatesThenShouldReturnEmptyList() {
        List<GiftCertificate> orderGiftCertificates = orderDao.findOrderGiftCertificates(100031);
        assertTrue(orderGiftCertificates.isEmpty());
    }

    @Test
    void whenCheckIfCertificateUsedThenShouldReturnTrue() {
        boolean condition = orderDao.checkIfCertificateUsed(1);
        assertTrue(condition);
    }

    @Test
    void whenCheckIfCertificateUsedThenShouldReturnFalse() {
        boolean condition = orderDao.checkIfCertificateUsed(10000);
        assertFalse(condition);
    }

    @Test
    void whenAddCorrectOrderThenShouldReturnOrderId() {
        Order order = new Order();
        order.setCost(new BigDecimal("123.0"));
        User user = userDao.findById(1).get();
        order.setUser(user);
        long actual = orderDao.add(order);
        assertEquals(8, actual);
    }

    @Test
    void whenAddIncorrectOrderThenShouldThrowException() {
        Order order = new Order();
        assertThrows(PersistenceException.class, () -> orderDao.add(order));
    }

    @Test
    void whenRemoveByIdThenShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> orderDao.removeById(1));
    }

    @Test
    void whenUpdateOrderThenShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> orderDao.update(null));
    }
}