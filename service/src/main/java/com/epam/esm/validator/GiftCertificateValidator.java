package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GiftCertificateValidator {
    private final String REGEX_NAME_AND_DESCRIPTION = "[а-яА-Я\\w\\s\\d\\.,?!]{1,250}";
    private final String REGEX_PRICE = "(0\\.\\d{1,2})|([1-9]\\d{0,5}(\\.\\d{1,2})?)|(1000000)";
    private final String REGEX_DURATION = "([1-9])|([1-2]\\d)|(30)";

    public void isValidField(GiftCertificateField giftCertificateField) {
        GiftCertificateField.FieldName fieldName = GiftCertificateField.FieldName.valueOf(giftCertificateField.getFieldName().toUpperCase());
        switch (fieldName) {
            case NAME:
                isValidName(giftCertificateField.getFieldValue());
                break;
            case DESCRIPTION:
                isValidDescription(giftCertificateField.getFieldValue());
                break;
            case PRICE:
                isValidPrice(giftCertificateField.getFieldValue());
                break;
            case DURATION:
                isValidDuration(giftCertificateField.getFieldValue());
                break;
        }
    }

    private void isValidName(String name) {
        if (name == null || name.isEmpty() || !name.matches(REGEX_NAME_AND_DESCRIPTION)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_NAME, name);
        }
    }

    private void isValidDescription(String description) {
        if (description == null || description.isEmpty() || !description.matches(REGEX_NAME_AND_DESCRIPTION)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_DESCRIPTION, description);
        }
    }

    private void isValidPrice(String price) {
        if (price == null || price.isEmpty() || !price.matches(REGEX_PRICE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_PRICE, price);
        }
    }

    private void isValidDuration(String duration) {
        if (duration == null || duration.isEmpty() || !duration.matches(REGEX_DURATION)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_DURATION, duration);
        }
    }
}