package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    @Query(value = "SELECT o.user FROM Order o GROUP BY o.user.userId ORDER BY SUM(o.cost) DESC ")
    List<User> findUserWithHighestCostOfAllOrders();

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);
}
