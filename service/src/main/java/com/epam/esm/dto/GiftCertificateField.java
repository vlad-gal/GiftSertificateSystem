package com.epam.esm.dto;

import com.epam.esm.validator.annotation.FieldNameValue;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GiftCertificateField {
    @NotBlank
    @FieldNameValue(enumClass = FieldName.class)
    private String fieldName;
    //    todo придумать аннотацию или обработку
    private String fieldValue;

    public enum FieldName {
        NAME,
        DESCRIPTION,
        PRICE,
        DURATION
    }
}