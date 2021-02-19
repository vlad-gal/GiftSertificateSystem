package com.epam.esm.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionPropertyKey {
    public final String TAG_WITH_NAME_NOT_FOUND = "tag.name.notFound";
    public final String TAG_WITH_ID_NOT_FOUND = "tag.id.not.found";
    public final String GIFT_CERTIFICATE_WITH_ID_NOT_FOUND = "gift.certificate.id.not.found";
    public final String INCORRECT_ID = "incorrect.id";
    public final String INCORRECT_GIFT_CERTIFICATE_NAME = "incorrect.gift.certificate.name";
    public final String INCORRECT_TAG_NAME = "incorrect.tag.name";
    public final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION = "incorrect.gift.certificate.description";
    public final String INCORRECT_PRICE = "incorrect.price";
    public final String INCORRECT_DURATION = "incorrect.duration";
    public final String INCORRECT_ORDER = "incorrect.order";
    public final String INCORRECT_FIELD_NAME = "incorrect.field.name";
    public final String INCORRECT_FIELD_VALUE = "incorrect.field.value";
    public final String INCORRECT_PAGE = "incorrect.page";
    public final String INCORRECT_USER_NAME = "incorrect.username";
    public final String INCORRECT_LOGIN = "incorrect.login";
    public final String USER_WITH_ID_NOT_FOUND = "user.id.not.found";
    public final String USER_ORDER_NOT_FOUND = "user.order.not.found";
    public final String USER_WITH_HIGHEST_COST_ORDERS_NOT_FOUND = "user.highest.cost.orders.not.found";
    public final String ORDER_WITH_ID_NOT_FOUND = "order.id.not.found";
    public final String CANNOT_DELETE_GIFT_CERTIFICATE = "cannot.delete.gift.certificate";
    public static final String MIN_DURATION = "";
    public static final String MAX_DURATION = "";
    public static final String MAX_PRICE = "";
    public static final String MIN_PRICE = "";

}