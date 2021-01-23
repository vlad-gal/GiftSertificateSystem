package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class GiftCertificateValidator {
    private final String REGEX_NAME_AND_DESCRIPTION = "[а-яА-Я\\w\\s\\d\\.,?!]{1,250}";
    private final long MIN_ID = 1;
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

    public void isValidId(long id) {
        if (id < MIN_ID) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_ID, id);
        }
    }

    private void isValidName(String name) {
        if (name == null || name.isEmpty() || !name.matches(REGEX_NAME_AND_DESCRIPTION)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_NAME, name);
        }
    }

    private void isValidDescription(String description) {
        if (description != null && !description.isEmpty() && !description.matches(REGEX_NAME_AND_DESCRIPTION)) {
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
}