package com.epam.esm.dao;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ColumnName {
    /*
    Tag table column name
     */
    public static final String TAG_TABLE = "tags";
    public static final String TAG_ID = "tagId";
    public static final String TAG_NAME = "tagName";
    /*
    Gift certificate table column name
     */
    public static final String CERTIFICATE_TABLE = "gift_certificates";
    public static final String CERTIFICATE_ID = "certificateId";
    public final String NAME = "name";
    public final String DESCRIPTION = "description";
    public final String PRICE = "price";
    public final String DURATION = "duration";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";
    /*
    User table column name
     */
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "userId";
    public final String LOGIN = "login";
    public final String FIRST_NAME = "firstName";
    public final String LAST_NAME = "lastName";
    /*
    Order table column name
     */
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID = "orderId";
    public static final String PURCHASE_DATE = "purchase_date";
    public final String COST = "cost";
    /*
    Joined tables
     */
    public static final String CERTIFICATES_HAS_TAGS_TABLE = "certificates_has_tags";
    public static final String ORDERS_HAS_GIFT_CERTIFICATE_TABLE = "orders_has_gift_certificate";
}