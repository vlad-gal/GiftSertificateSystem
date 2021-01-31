package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class QueryParameterValidator {
    private final String REGEX_TAG_NAME_VALUE = "[а-яА-Я\\w\\s\\d\\.?!]{1,45}";
    private final String REGEX_TAG_NAME_KEY = "tagName([1-9]\\d*)?";
    private final String REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION_VALUE = "[а-яА-Я\\w\\s\\d\\.,?!]{1,250}";
    private final String REGEX_GIFT_CERTIFICATE_NAME_KEY = "name";
    private final String REGEX_GIFT_CERTIFICATE_DESCRIPTION_KEY = "description";
    private final String REGEX_ORDER_VALUE = "[-]?name|[-]?description|[-]?id";
    private final String REGEX_ORDER_KEY = "order";
    private final String REGEX_PAGE_KEY = "page";
    private final String REGEX_PER_PAGE_KEY = "per_page";
    private final String REGEX_PAGE_VALUE = "[1-9]\\d*";
    private final String REGEX_LOGIN_KEY = "login";
    private final String REGEX_LOGIN_VALUE = "\\w{1,20}";
    private final String REGEX_FIRST_NAME_KEY = "first_name";
    private final String REGEX_LAST_NAME_KEY = "last_name";
    private final String REGEX_USER_NAME_VALUE = "[A-ZА-Я][а-яa-z]{0,19}";

    public void isValidGiftCertificateQueryParameters(Map<String, String> queryParameters) {
        queryParameters.forEach((key, value) -> {
            if (key.matches(REGEX_TAG_NAME_KEY)) {
                isValidTagName(value);
            }
        });
        isValidGiftCertificateName(queryParameters.get(REGEX_GIFT_CERTIFICATE_NAME_KEY));
        isValidGiftCertificateDescription(queryParameters.get(REGEX_GIFT_CERTIFICATE_DESCRIPTION_KEY));
        isValidOrderType(queryParameters.get(REGEX_ORDER_KEY));
        isValidPage(queryParameters.get(REGEX_PAGE_KEY));
        isValidPage(queryParameters.get(REGEX_PER_PAGE_KEY));
    }

    public void isValidTagQueryParameters(Map<String, String> queryParameters) {
        queryParameters.forEach((key, value) -> {
            if (key.matches(REGEX_TAG_NAME_KEY)) {
                isValidTagName(value);
            }
        });
        isValidPage(queryParameters.get(REGEX_PAGE_KEY));
        isValidPage(queryParameters.get(REGEX_PER_PAGE_KEY));
    }

    public void isValidUserQueryParameters(Map<String, String> queryParameters) {
        isValidName(queryParameters.get(REGEX_FIRST_NAME_KEY));
        isValidName(queryParameters.get(REGEX_LAST_NAME_KEY));
        isValidLogin(queryParameters.get(REGEX_LOGIN_KEY));
        isValidPage(queryParameters.get(REGEX_PAGE_KEY));
        isValidPage(queryParameters.get(REGEX_PER_PAGE_KEY));
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

    private static void isValidPage(String page) {
        if (page != null && !page.isEmpty() && !page.matches(REGEX_PAGE_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_PAGE, page);
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