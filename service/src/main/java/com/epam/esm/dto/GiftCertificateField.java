package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.validator.annotation.FieldNameValue;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GiftCertificateField {
    @NotBlank(message = ExceptionPropertyKey.INCORRECT_FIELD_NAME)
    @FieldNameValue(enumClass = FieldName.class)
    private String fieldName;
    private String fieldValue;

    public enum FieldName {
        NAME,
        DESCRIPTION,
        PRICE,
        DURATION
    }
}