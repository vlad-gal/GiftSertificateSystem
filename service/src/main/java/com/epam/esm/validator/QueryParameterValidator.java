package com.epam.esm.validator;

import com.epam.esm.util.QueryParameter;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QueryParameterValidator {
    private final String REGEX_TAG_NAME = "[à-ÿÀ-ß\\w\\s\\d\\.,?!]{1,45}";
    private final String REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION = "[à-ÿÀ-ß\\w\\s\\d\\.,?!]{1,250}";
    private final String REGEX_ORDER = "name|description";
    private final String REGEX_DIRECTION = "asc|desc";

    public void isValidQueryParameters(QueryParameter queryParameter) {
        isValidTagName(queryParameter.getTagName());
        isValidGiftCertificateName(queryParameter.getCertificateName());
        isValidGiftCertificateDescription(queryParameter.getCertificateDescription());
        isValidOrderType(queryParameter.getOrder());
        isValidSortDirection(queryParameter.getDirection());
    }

    private void isValidTagName(String tagName) {
        if (tagName != null && !tagName.isEmpty() && !tagName.matches(REGEX_TAG_NAME)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_TAG_NAME, tagName);
        }
    }

    private void isValidGiftCertificateName(String giftCertificateName) {
        if (giftCertificateName != null && !giftCertificateName.isEmpty()
                && !giftCertificateName.matches(REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_NAME, giftCertificateName);
        }
    }

    private void isValidGiftCertificateDescription(String giftCertificateDescription) {
        if (giftCertificateDescription != null && !giftCertificateDescription.isEmpty()
                && !giftCertificateDescription.matches(REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_DESCRIPTION,
                    giftCertificateDescription);
        }
    }

    private void isValidOrderType(String order) {
        if (order != null && !order.isEmpty() && !order.matches(REGEX_ORDER)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_ORDER, order);
        }
    }

    private void isValidSortDirection(String direction) {
        if (direction != null && !direction.isEmpty() && !direction.matches(REGEX_DIRECTION)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_DIRECTION, direction);
        }
    }
}