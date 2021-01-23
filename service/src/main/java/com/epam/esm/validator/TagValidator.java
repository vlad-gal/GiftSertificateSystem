package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagValidator {
    private final long MIN_ID = 1;
    private final String REGEX_NAME = "[а-яА-Я\\w\\s\\d\\.,?!]{1,45}";

    public void isValidTag(TagDto tagDto) {
        isValidName(tagDto.getName());
    }

    public void isValidId(long id) {
        if (id < MIN_ID) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_ID, id);
        }
    }

    private void isValidName(String name) {
        if (name == null || name.isEmpty() || !name.matches(REGEX_NAME)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_TAG_NAME, name);
        }
    }
}