package com.epam.esm.dao.impl;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PersistenceConfig.class})
@ActiveProfiles("test")
@Transactional
class UserDaoImplTest {
    @Autowired
    private UserDao userDao;
    private HashMap<String, String> defaultQueryParameters;

    {
        defaultQueryParameters = new HashMap<>();
        defaultQueryParameters.put("page", "1");
        defaultQueryParameters.put("per_page", "2");
    }

    @Test
    void whenFindAllByParametersThenShouldReturnListOfUsers() {
        List<User> users = userDao.findAllByParameters(defaultQueryParameters);
        assertEquals(2, users.size());
    }

    @Test
    void whenFindAllByParametersThenShouldThrowException() {
        assertThrows(NullPointerException.class, () -> userDao.findAllByParameters(null));
    }

    @Test
    void whenFindUserWithHighestCostOfAllOrdersThenShouldReturnUser() {
        Optional<User> user = userDao.findUserWithHighestCostOfAllOrders();
        boolean condition = user.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByExistIdThenShouldReturnUserId() {
        Optional<User> user = userDao.findById(1);
        boolean condition = user.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByNotExistIdThenShouldReturnEmpty() {
        Optional<User> user = userDao.findById(17754);
        boolean condition = user.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenAddUserThenShouldThrowException() {
        User user = new User();
        assertThrows(UnsupportedOperationException.class, () -> userDao.add(user));
    }

    @Test
    void whenRemoveByIdThenShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> userDao.removeById(1));
    }

    @Test
    void whenUpdateUserThenShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> userDao.update(null));
    }
}