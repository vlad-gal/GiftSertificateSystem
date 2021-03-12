package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o FROM Order o JOIN o.giftCertificates g WHERE g.id=:id")
    List<Order> findOrdersWhereGiftCertificateUsed(@Param("id") long id);

    @Query(value = "SELECT COUNT(t.tagId) AS widely_tag FROM Order o JOIN o.giftCertificates g " +
            "JOIN g.tags t JOIN o.user u WHERE u.userId = :userId GROUP BY t.tagId ORDER BY widely_tag DESC ")
    List<Long> findMostWidelyUsedTagByUserId(@Param("userId") long userId);

    List<Order> findOrdersByUserUserId(long userId);

    Optional<Order> findOrderByOrderIdAndUserUserId(long orderId, long userId);

    @Query(value = "SELECT g FROM Order o JOIN o.giftCertificates g WHERE o.orderId=:orderId AND o.user.userId = :userId")
    List<GiftCertificate> findUserOrderGiftCertificates(@Param("orderId") long orderId, @Param("userId") long userId);
}