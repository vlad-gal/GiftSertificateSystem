package com.epam.esm.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorField {
    private String fieldName;
    private String errorCode;
    private Object rejectedValue;
    private Object errorMessage;
}