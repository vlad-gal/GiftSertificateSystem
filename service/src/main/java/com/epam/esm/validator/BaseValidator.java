//package com.epam.esm.validator;
//
//import com.epam.esm.exception.ExceptionPropertyKey;
//import com.epam.esm.exception.ValidationException;
//
//public abstract class BaseValidator {
//    private static final long MIN_ID = 1;
//
//    public static void isValidId(long id) {
//        if (id < MIN_ID) {
//            throw new ValidationException(ExceptionPropertyKey.INCORRECT_ID, id);
//        }
//    }
//}