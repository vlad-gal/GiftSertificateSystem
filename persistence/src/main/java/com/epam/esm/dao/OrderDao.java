package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends BaseDao<Order> {
    long findMostWidelyUsedTag(long userId);

    List<Order> findUserOrders(long userId);

    Optional<Order> findUserOrder(long userId, long orderId);

    List<GiftCertificate> findOrderGiftCertificates(long orderId);

    boolean checkIfCertificateUsed(long certificateId);
}