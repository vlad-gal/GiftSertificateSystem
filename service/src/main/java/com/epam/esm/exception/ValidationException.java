package com.epam.esm.exception;

public class ValidationException extends RuntimeException {
    private String messageKey;
    private Object[] messageValues;

    public ValidationException(String messageKey, Object... messageValues) {
        this.messageKey = messageKey;
        this.messageValues = messageValues;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getMessageValues() {
        return messageValues;
    }
}