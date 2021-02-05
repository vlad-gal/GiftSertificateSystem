package com.epam.esm.validator;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserValidator extends BaseValidator {
    private final String REGEX_NAME = "[A-ZА-Я][а-яa-z]{1,19}";
    private final String REGEX_LOGIN = "\\w{1,20}";

    public void isValidUser(UserDto userDto) {
        isValidName(userDto.getFirstName());
        isValidName(userDto.getLastName());
        isValidLogin(userDto.getLogin());
    }

    private void isValidName(String name) {
        if (name == null || name.isEmpty() || !name.matches(REGEX_NAME)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_USER_NAME, name);
        }
    }

    private void isValidLogin(String login) {
        if (login == null || login.isEmpty() || !login.matches(REGEX_LOGIN)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_LOGIN, login);
        }
    }
}