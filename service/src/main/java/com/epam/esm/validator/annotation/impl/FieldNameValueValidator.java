package com.epam.esm.validator.annotation.impl;

import com.epam.esm.validator.annotation.FieldNameValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldNameValueValidator implements ConstraintValidator<FieldNameValue, String> {
    private List<String> acceptedValues;

    @Override
    public void initialize(FieldNameValue constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return acceptedValues.contains(value.toUpperCase());
    }
}