package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.exception.ValidationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GiftCertificateValidatorTest {

    public static Object[][] correctGiftCertificate() {
        GiftCertificateDto giftCertificate1 = new GiftCertificateDto();
        giftCertificate1.setName("Hello");
        giftCertificate1.setDescription("Hello from description");
        giftCertificate1.setDuration(1);
        giftCertificate1.setPrice(new BigDecimal("23.3"));

        GiftCertificateDto giftCertificate2 = new GiftCertificateDto();
        giftCertificate2.setName("Hello 2");
        giftCertificate2.setDescription("Hello from description 2");
        giftCertificate2.setDuration(2);
        giftCertificate2.setPrice(new BigDecimal("34"));

        GiftCertificateDto giftCertificate3 = new GiftCertificateDto();
        giftCertificate3.setName("Hello 3");
        giftCertificate3.setDescription("Hello from description 3");
        giftCertificate3.setDuration(2);
        giftCertificate3.setPrice(new BigDecimal("23"));
        return new Object[][]{{giftCertificate1}, {giftCertificate2}, {giftCertificate3}};
    }

    @ParameterizedTest
    @MethodSource("correctGiftCertificate")
    void whenIsValidGiftCertificateThenShouldNotThrowException(GiftCertificateDto giftCertificateDto) {
        assertDoesNotThrow(() -> GiftCertificateValidator.isValidGiftCertificate(giftCertificateDto));
    }

    public static Object[][] incorrectGiftCertificate() {
        GiftCertificateDto giftCertificate1 = new GiftCertificateDto();
        giftCertificate1.setName("");
        giftCertificate1.setDescription("Hello from description");
        giftCertificate1.setDuration(1);
        giftCertificate1.setPrice(new BigDecimal("23.3"));

        GiftCertificateDto giftCertificate2 = new GiftCertificateDto();
        giftCertificate2.setName("Hello 2");
        giftCertificate2.setDescription("@@");
        giftCertificate2.setDuration(2);
        giftCertificate2.setPrice(new BigDecimal("34"));

        GiftCertificateDto giftCertificate3 = new GiftCertificateDto();
        giftCertificate3.setName("Hello 3");
        giftCertificate3.setDescription("Hello from description 3");
        giftCertificate3.setDuration(0);
        giftCertificate3.setPrice(new BigDecimal("23"));

        GiftCertificateDto giftCertificate4 = new GiftCertificateDto();
        giftCertificate4.setName("Hello 3");
        giftCertificate4.setDescription("Hello from description 3");
        giftCertificate4.setDuration(2);
        giftCertificate4.setPrice(new BigDecimal(-1450));
        return new Object[][]{{giftCertificate1}, {giftCertificate2}, {giftCertificate3}, {giftCertificate4}};
    }

    @ParameterizedTest
    @MethodSource("incorrectGiftCertificate")
    void whenIsNotValidGiftCertificateThenShouldThrowException(GiftCertificateDto giftCertificateDto) {
        assertThrows(ValidationException.class, () -> GiftCertificateValidator.isValidGiftCertificate(giftCertificateDto));
    }

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
        GiftCertificateField incorrectField = new GiftCertificateField();
        incorrectField.setFieldName("incorrect");
        incorrectField.setFieldValue("2313");
        return new Object[][]{
                {price}, {name}, {description}, {duration}, {incorrectField}
        };
    }

    @ParameterizedTest
    @MethodSource("incorrectFieldsForUpdateGiftCertificateField")
    void whenIsNotValidFieldThenShouldThrowException(GiftCertificateField giftCertificateField) {
        assertThrows(ValidationException.class, () -> GiftCertificateValidator.isValidField(giftCertificateField));
    }
}