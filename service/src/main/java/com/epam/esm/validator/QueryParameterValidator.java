package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Map;

@UtilityClass
public class QueryParameterValidator {
    private final String REGEX_TAG_NAME_VALUE = "[а-яА-Я\\w\\s\\d\\.?!]{1,45}";
    private final String REGEX_TAG_NAME_KEY = "tagName";
    private final String COMMA = ",";
    private final String REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION_VALUE = "[а-яА-Я\\w\\s\\d\\.,?!]{1,250}";
    private final String REGEX_GIFT_CERTIFICATE_NAME_KEY = "name";
    private final String REGEX_GIFT_CERTIFICATE_DESCRIPTION_KEY = "description";
    private final String REGEX_ORDER_VALUE = "[-]?name|[-]?description|[-]?id|[-]?firstName|[-]?lastName|[-]?login";
    private final String REGEX_ORDER_KEY = "order";
    private final String REGEX_LOGIN_KEY = "login";
    private final String REGEX_LOGIN_VALUE = "\\w{1,20}";
    private final String REGEX_FIRST_NAME_KEY = "firstName";
    private final String REGEX_LAST_NAME_KEY = "lastName";
    private final String REGEX_USER_NAME_VALUE = "[A-ZА-Я][а-яa-z]{0,19}";

    public void isValidGiftCertificateQueryParameters(Map<String, String> queryParameters) {
        queryParameters.forEach((key, value) -> {
            if (key.matches(REGEX_TAG_NAME_KEY)) {
                Arrays.stream(value.split(COMMA)).forEach(QueryParameterValidator::isValidTagName);
            }
        });
        isValidGiftCertificateName(queryParameters.get(REGEX_GIFT_CERTIFICATE_NAME_KEY));
        isValidGiftCertificateDescription(queryParameters.get(REGEX_GIFT_CERTIFICATE_DESCRIPTION_KEY));
        isValidOrderType(queryParameters.get(REGEX_ORDER_KEY));
    }

    public void isValidTagQueryParameters(Map<String, String> queryParameters) {
        queryParameters.forEach((key, value) -> {
            if (key.matches(REGEX_TAG_NAME_KEY)) {
                isValidTagName(value);
            }
        });
        isValidOrderType(queryParameters.get(REGEX_ORDER_KEY));
    }

    public void isValidUserQueryParameters(Map<String, String> queryParameters) {
        isValidName(queryParameters.get(REGEX_FIRST_NAME_KEY));
        isValidName(queryParameters.get(REGEX_LAST_NAME_KEY));
        isValidLogin(queryParameters.get(REGEX_LOGIN_KEY));
        isValidOrderType(queryParameters.get(REGEX_ORDER_KEY));
    }

    private void isValidLogin(String login) {
        if (login != null && !login.isEmpty() && !login.matches(REGEX_LOGIN_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_LOGIN, login);
        }
    }

    private void isValidName(String name) {
        if (name != null && !name.isEmpty() && !name.matches(REGEX_USER_NAME_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_USER_NAME, name);
        }
    }

    private void isValidTagName(String tagName) {
        if (tagName != null && !tagName.isEmpty() && !tagName.matches(REGEX_TAG_NAME_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_TAG_NAME, tagName);
        }
    }

    private void isValidGiftCertificateName(String giftCertificateName) {
        if (giftCertificateName != null && !giftCertificateName.isEmpty()
                && !giftCertificateName.matches(REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_NAME, giftCertificateName);
        }
    }

    private void isValidGiftCertificateDescription(String giftCertificateDescription) {
        if (giftCertificateDescription != null && !giftCertificateDescription.isEmpty()
                && !giftCertificateDescription.matches(REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_DESCRIPTION,
                    giftCertificateDescription);
        }
    }

    private void isValidOrderType(String order) {
        if (order != null && !order.isEmpty() && !order.matches(REGEX_ORDER_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_ORDER, order);
        }
    }
}