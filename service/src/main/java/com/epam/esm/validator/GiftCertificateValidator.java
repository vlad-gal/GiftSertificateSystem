package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class GiftCertificateValidator extends BaseValidator{
    private final String REGEX_NAME_AND_DESCRIPTION = "[а-яА-Я\\w\\s\\d\\.,?!]{1,250}";
    private final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private final BigDecimal MAX_PRICE = new BigDecimal("1000000");
    private final int MIN_DURATION = 1;
    private final int MAX_DURATION = 30;

    public void isValidGiftCertificate(GiftCertificateDto giftCertificateDto) {
        isValidName(giftCertificateDto.getName());
        isValidDescription(giftCertificateDto.getDescription());
        isValidPrice(giftCertificateDto.getPrice());
        isValidDuration(giftCertificateDto.getDuration());
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

    private void isValidPrice(BigDecimal price) {
        if (price == null || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_PRICE, price);
        }
    }

    private void isValidDuration(int duration) {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_DURATION, duration);
        }
    }

    public void isValidField(GiftCertificateField giftCertificateField) {
        try {
            GiftCertificateField.FieldName fieldName = GiftCertificateField.FieldName.valueOf(giftCertificateField.getFieldName().toUpperCase());
            switch (fieldName) {
                case NAME:
                    isValidName(giftCertificateField.getFieldValue());
                    break;
                case DESCRIPTION:
                    isValidDescription(giftCertificateField.getFieldValue());
                    break;
                case PRICE:
                    isValidPrice(new BigDecimal(giftCertificateField.getFieldValue()));
                    break;
                case DURATION:
                    isValidDuration(Integer.parseInt(giftCertificateField.getFieldValue()));
                    break;
            }
        } catch (NumberFormatException exception) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_FIELD_VALUE, giftCertificateField.getFieldValue(), giftCertificateField.getFieldName());
        } catch (IllegalArgumentException exception) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_FIELD_NAME, giftCertificateField.getFieldName());
        }
    }
}