package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.QUser;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PersistenceConfig.class})
@EnableJpaRepositories("com.epam.esm")
@ActiveProfiles("test")
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindByExistIdThenShouldReturnUserId() {
        Optional<User> user = userRepository.findById(1L);
        boolean condition = user.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByNotExistIdThenShouldReturnEmpty() {
        Optional<User> user = userRepository.findById(17754L);
        boolean condition = user.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenFindAllThenShouldReturnListOfUsers() {
        List<User> users = userRepository.findAll();
        assertEquals(5, users.size());
    }

    @Test
    void whenFindAllWithPageableThenShouldReturnPageOfUsers() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<User> users = userRepository.findAll(pageable);
        assertEquals(2, users.getContent().size());
    }

    @Test
    void whenFindAllWithPredicateThenShouldReturnPageOfUsers() {
        Predicate predicate = ExpressionUtils.allOf(QUser.user.firstName.startsWith("Гри"));
        Pageable pageable = PageRequest.of(0, 2);
        Page<User> users = userRepository.findAll(predicate, pageable);
        assertEquals(1, users.getContent().size());
    }

    @Test
    void whenFindByLoginThenShouldReturnUser() {
        Optional<User> user = userRepository.findByLogin("josh");
        boolean condition = user.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByLoginThenShouldReturnEmpty() {
        Optional<User> user = userRepository.findByLogin("123");
        boolean condition = user.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenFindUserWithHighestCostOfAllOrdersThenShouldReturnUser() {
        List<User> users = userRepository.findUserWithHighestCostOfAllOrders();
        boolean condition = users.isEmpty();
        assertFalse(condition);
    }

    @Test
    void whenFindExistUserByLoginThenShouldReturnUser() {
        boolean condition = userRepository.existsByLogin("josh");
        assertTrue(condition);
    }

    @Test
    void whenSaveUserThenShouldReturnUser() {
        Role role = new Role();
        role.setRoleName("User");
        User expected = new User();
        expected.setLogin("qwee122");
        expected.setFirstName("Gjdj");
        expected.setLastName("Hi");
        expected.setRole(role);
        expected.setPassword("1233");
        User actual = userRepository.save(expected);
        assertEquals(expected, actual);
    }
}