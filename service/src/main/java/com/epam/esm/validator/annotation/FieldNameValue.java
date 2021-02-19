package com.epam.esm.validator.annotation;

import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.validator.annotation.impl.FieldNameValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldNameValueValidator.class)
public @interface FieldNameValue {
    Class<GiftCertificateField.FieldName> enumClass();

    String message() default ExceptionPropertyKey.INCORRECT_FIELD_NAME;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
