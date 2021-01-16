package com.epam.esm.handler;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorCode {
    public final int NOT_FOUND = 404404;
    public final int BAD_REQUEST = 400400;
    public final int METHOD_NOT_ALLOWED = 405405;
    public final int INTERNAL_ERROR = 500500;
}