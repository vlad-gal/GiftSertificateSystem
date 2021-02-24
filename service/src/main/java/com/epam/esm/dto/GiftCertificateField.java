package com.epam.esm.dto;

import lombok.Data;

@Data
public class GiftCertificateField {
    private String fieldName;
    private String fieldValue;

    public enum FieldName {
        NAME,
        DESCRIPTION,
        PRICE,
        DURATION
    }
}