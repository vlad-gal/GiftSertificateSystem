package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {PersistenceConfig.class})
@ActiveProfiles("test")
@Transactional
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void whenFindMostWidelyUsedTagThenShouldReturnTagId() {
        List<Long> tagIds = orderRepository.findMostWidelyUsedTagByUserId(1);
        assertEquals(5, tagIds.get(0));
    }

    @Test
    void whenSaveCorrectOrderThenShouldReturnOrder() {
        Role role = new Role();
        role.setRoleName("User");
        Order expected = new Order();
        expected.setCost(new BigDecimal("123.0"));
        User user = new User();
        user.setLogin("qwee122");
        user.setFirstName("Gjdj");
        user.setLastName("Hi");
        user.setRole(role);
        user.setPassword("1233");
        expected.setUser(user);
        Order actual = orderRepository.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void whenFindOrdersWhereGiftCertificateUsedThenShouldReturnListOfOrders() {
        List<Order> orders = orderRepository.findOrdersWhereGiftCertificateUsed(1L);
        boolean condition = orders.isEmpty();
        assertFalse(condition);
    }

    @Test
    void whenFindOrdersByUserIdThenShouldReturnListOfOrders() {
        List<Order> orders = orderRepository.findOrdersByUserUserId(1L);
        assertEquals(3, orders.size());
    }

    @Test
    void whenFindOrderByOrderIdAndUserIdThenShouldReturnOrder() {
        Optional<Order> optionalOrder = orderRepository.findOrderByOrderIdAndUserUserId(1, 1);
        boolean condition = optionalOrder.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindUserOrderGiftCertificatesThenShouldReturnListOfGiftCertificates() {
        List<GiftCertificate> certificates = orderRepository.findUserOrderGiftCertificates(1, 1);
        assertEquals(5, certificates.size());
    }
}