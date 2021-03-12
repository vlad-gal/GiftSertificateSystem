package com.epam.esm.handler;

import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UserAlreadyExistException;
import com.epam.esm.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    private static final String INCORRECT_TYPE = "incorrect.type";
    private static final String PAGE_NOT_FOUND = "page.not.found";
    private static final String METHOD_NOT_SUPPORT = "method.not.support";
    private static final String BODY_MISSING = "body.missing";
    private static final String INTERNAL_ERROR = "internal.error";
    private static final String ACCESS_DENIED = "access.denied";
    private static final String INCORRECT_CREDENTIALS = "incorrect.credentials";
    private static final String INCORRECT_VALUE = "incorrect.value";
    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorHandler> handleValidationException(ValidationException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getMessageKey(), exception.getMessageValues(), locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.BAD_REQUEST);
        log.error("ValidationException message: {}", message);
        return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorHandler> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        Locale locale) {
        String message = messageSource.getMessage(exception.getMessageKey(), exception.getMessageValues(), locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.NOT_FOUND);
        log.error("ResourceNotFoundException message: {}", message);
        return new ResponseEntity<>(errorHandler, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeleteResourceException.class)
    public ResponseEntity<ErrorHandler> handleCannotDeleteResourceException(DeleteResourceException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getMessageKey(), exception.getMessageValues(), locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.BAD_REQUEST);
        log.error("CannotDeleteResourceException message: {}", message);
        return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorHandler> handleTypeMismatchException(TypeMismatchException exception, Locale locale) {
        String message = messageSource.getMessage(INCORRECT_TYPE, new Object[]{exception.getValue()}, locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.BAD_REQUEST);
        log.error("TypeMismatchException message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorHandler> handleNoHandlerFoundException(NoHandlerFoundException exception, Locale locale) {
        String message = messageSource.getMessage(PAGE_NOT_FOUND, null, locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.NOT_FOUND);
        log.error("NoHandlerFoundException message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorHandler> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception, Locale locale) {
        String message = messageSource.getMessage(METHOD_NOT_SUPPORT, null, locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.METHOD_NOT_ALLOWED);
        log.error("HttpRequestMethodNotSupportedException message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorHandler> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception,
                                                                              Locale locale) {
        String message = messageSource.getMessage(BODY_MISSING, null, locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.BAD_REQUEST);
        log.error("HttpMessageNotReadableException message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorHandler> handleException(Exception exception, Locale locale) {
        String message = messageSource.getMessage(INTERNAL_ERROR, null, locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.INTERNAL_ERROR);
        log.error("Exception message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorHandler> handleAccessDeniedException(AccessDeniedException exception, Locale locale) {
        String message = messageSource.getMessage(ACCESS_DENIED, null, locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.ACCESS_DENIED);
        log.error("AccessDeniedException message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorHandler> handleBadCredentialsException(BadCredentialsException exception, Locale locale) {
        String message = messageSource.getMessage(INCORRECT_CREDENTIALS, null, locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.BAD_REQUEST);
        log.error("BadCredentialsException message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorHandler> handleUserAlreadyExistException(UserAlreadyExistException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getMessageKey(), exception.getMessageValues(), locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.BAD_REQUEST);
        log.error("UserAlreadyExistException message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorHandler> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, Locale locale) {
        String message = messageSource.getMessage(INCORRECT_VALUE, null, locale);
        ErrorHandler errorHandler = new ErrorHandler(message, ErrorCode.BAD_REQUEST);
        List<ErrorField> errorFields = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            ErrorField errorField = new ErrorField();
            errorField.setFieldName(fieldError.getField());
            errorField.setErrorCode(fieldError.getCode());
            errorField.setRejectedValue(fieldError.getRejectedValue());
            if (fieldError.getDefaultMessage() != null) {
                errorField.setErrorMessage(messageSource.getMessage(fieldError.getDefaultMessage(), new Object[]{fieldError.getRejectedValue()}, locale));
            }
            errorFields.add(errorField);
        });
        errorHandler.setErrorFields(errorFields);
        log.error("MethodArgumentNotValidException message: {}", exception.getMessage());
        return new ResponseEntity<>(errorHandler, HttpStatus.BAD_REQUEST);
    }
}