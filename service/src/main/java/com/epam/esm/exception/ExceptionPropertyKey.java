package com.epam.esm.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionPropertyKey {
    public final String TAG_WITH_NAME_NOT_FOUND = "tagWithNameNotFound";
    public final String TAG_WITH_ID_NOT_FOUND = "tagWithIdNotFound";
    public final String GIFT_CERTIFICATE_WITH_ID_NOT_FOUND = "giftCertificateWithIdNotFound";
    public final String INCORRECT_ID = "incorrectId";
    public final String INCORRECT_GIFT_CERTIFICATE_NAME = "incorrectGiftCertificateName";
    public final String INCORRECT_TAG_NAME = "incorrectTagName";
    public final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION = "incorrectGiftCertificateDescription";
    public final String INCORRECT_PRICE = "incorrectPrice";
    public final String INCORRECT_DURATION = "incorrectDuration";
    public final String INCORRECT_ORDER = "incorrectOrder";
    public final String INCORRECT_FIELD_NAME = "incorrectFieldName";
    public final String INCORRECT_FIELD_VALUE = "incorrectFieldValue";
    public final String INCORRECT_PAGE = "incorrectPage";
    public final String INCORRECT_USER_NAME = "incorrectUserName";
    public final String INCORRECT_LOGIN = "incorrectLogin";
    public final String USER_WITH_ID_NOT_FOUND = "userWithIdNotFound";
    public final String USER_ORDER_NOT_FOUND = "userOrderNotFound";
    public final String USER_WITH_HIGHEST_COST_ORDERS_NOT_FOUND = "userWithHighestCostOrdersNotFound";
    public final String ORDER_WITH_ID_NOT_FOUND = "orderWithIdNotFound";
    public final String CANNOT_DELETE_GIFT_CERTIFICATE = "cannotDeleteGiftCertificate";
}