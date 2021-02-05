package com.epam.esm.dao;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JPQLQuery {
    public final String SELECT_ALL_CERTIFICATES = "SELECT DISTINCT g FROM GiftCertificate g ";
    public final String DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM GiftCertificate WHERE id = ?1";
    public final String SELECT_ALL_TAGS = "SELECT t FROM Tag t ";
    public final String DELETE_TAG_BY_ID = "DELETE FROM Tag WHERE tagId = ?1";
    public final String SELECT_TAG_BY_NAME = "SELECT t FROM Tag t WHERE t.name = ?1";
    public final String SELECT_ALL_USERS = "SELECT u FROM User u ";
    public final String SELECT_TOP_USER_TAG =
            "SELECT COUNT(t.tagId) AS widely_tag FROM Order o JOIN o.giftCertificates g " +
                    "JOIN g.tags t JOIN o.user u WHERE u.userId = ?1 GROUP BY t.tagId ORDER BY widely_tag DESC ";
    public final String SELECT_USER_ORDERS = "SELECT o FROM Order o WHERE o.user.userId = ?1";
    public final String SELECT_USER_ORDER = "SELECT o FROM Order o WHERE o.user.userId = ?1 AND o.orderId =?2";
    public final String SELECT_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS =
            "SELECT o.user FROM Order o GROUP BY o.user.userId ORDER BY SUM(o.cost) DESC ";
    public final String SELECT_ORDER_GIFT_CERTIFICATES = "SELECT g FROM Order o JOIN o.giftCertificates g WHERE o.orderId=?1";
    public final String SELECT_ORDER_WITH_USED_GIFT_CERTIFICATES = "SELECT o FROM Order o JOIN o.giftCertificates g WHERE g.id=?1";
}
