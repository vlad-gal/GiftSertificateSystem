package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.exception.ValidationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GiftCertificateValidatorTest {

    public static Object[][] correctFieldsForUpdateGiftCertificateField() {
        GiftCertificateField price = new GiftCertificateField();
        price.setFieldName("price");
        price.setFieldValue("1233");
        GiftCertificateField name = new GiftCertificateField();
        name.setFieldName("name");
        name.setFieldValue("Upd name");
        GiftCertificateField description = new GiftCertificateField();
        description.setFieldName("description");
        description.setFieldValue("Upd desc");
        GiftCertificateField duration = new GiftCertificateField();
        duration.setFieldName("duration");
        duration.setFieldValue("1");
        return new Object[][]{
                {price}, {name}, {description}, {duration}
        };
    }

    @ParameterizedTest
    @MethodSource("correctFieldsForUpdateGiftCertificateField")
    void whenIsValidFieldThenShouldNotThrowException(GiftCertificateField giftCertificateField) {
        assertDoesNotThrow(() -> GiftCertificateValidator.isValidField(giftCertificateField));
    }

    public static Object[][] incorrectFieldsForUpdateGiftCertificateField() {
        GiftCertificateField price = new GiftCertificateField();
        price.setFieldName("price");
        price.setFieldValue("1233d");
        GiftCertificateField name = new GiftCertificateField();
        name.setFieldName("name");
        name.setFieldValue("Upd#@ name");
        GiftCertificateField description = new GiftCertificateField();
        description.setFieldName("description");
        description.setFieldValue("Upd desc1231!@!");
        GiftCertificateField duration = new GiftCertificateField();
        duration.setFieldName("duration");
        duration.setFieldValue("1ewr");
        return new Object[][]{
                {price}, {name}, {description}, {duration}
        };
    }

    @ParameterizedTest
    @MethodSource("incorrectFieldsForUpdateGiftCertificateField")
    void whenIsNotValidFieldThenShouldThrowException(GiftCertificateField giftCertificateField) {
        assertThrows(ValidationException.class, () -> GiftCertificateValidator.isValidField(giftCertificateField));
    }
}