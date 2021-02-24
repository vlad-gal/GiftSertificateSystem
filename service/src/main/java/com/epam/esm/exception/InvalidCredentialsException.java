package com.epam.esm.exception;

public class InvalidCredentialsException extends RuntimeException {
    private String messageKey;
    private Object[] messageValues;

    public InvalidCredentialsException(String messageKey, Object... messageValues) {
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