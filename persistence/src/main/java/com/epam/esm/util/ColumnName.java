package com.epam.esm.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";
    /*
    User table column name
     */
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "userId";
    /*
    Order table column name
     */
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID = "orderId";
    public static final String PURCHASE_DATE = "purchase_date";
    /*
    Permission table column name
     */
    public static final String PERMISSION_TABLE = "permissions";
    public static final String PERMISSION_ID = "permissionId";
    /*
    Role table column name
     */
    public static final String ROLE_TABLE = "roles";
    public static final String ROLE_ID = "roleId";
    /*
    Joined tables
     */
    public static final String CERTIFICATES_HAS_TAGS_TABLE = "certificates_has_tags";
    public static final String ORDERS_HAS_GIFT_CERTIFICATE_TABLE = "orders_has_gift_certificate";
    public static final String PERMISSIONS_HAS_ROLES_TABLE = "permissions_has_roles";
}