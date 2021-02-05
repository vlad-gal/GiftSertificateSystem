package com.epam.esm.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorHandler {
    private String errorMessage;
    private int errorCode;
}