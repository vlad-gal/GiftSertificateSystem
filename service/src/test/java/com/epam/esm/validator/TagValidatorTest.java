package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TagValidatorTest {

    public static Object[][] correctTag() {
        TagDto tagDto1 = new TagDto();
        tagDto1.setTagId(1);
        tagDto1.setName("Hello1");

        TagDto tagDto2 = new TagDto();
        tagDto2.setTagId(2);
        tagDto2.setName("Hello2");

        TagDto tagDto3 = new TagDto();
        tagDto3.setTagId(3);
        tagDto3.setName("Hello3");

        TagDto tagDto4 = new TagDto();
        tagDto4.setTagId(4);
        tagDto4.setName("Hello4");

        TagDto tagDto5 = new TagDto();
        tagDto5.setTagId(5);
        tagDto5.setName("Hello5");
        return new Object[][]{{tagDto1}, {tagDto2}, {tagDto3}, {tagDto4}, {tagDto5}};
    }

    @ParameterizedTest
    @MethodSource("correctTag")
    void whenIsValidTagThenShouldNotThrowException(TagDto tagDto) {
        assertDoesNotThrow(() -> TagValidator.isValidTag(tagDto));
    }

    public static Object[][] incorrectTag() {
        TagDto tagDto1 = new TagDto();
        tagDto1.setTagId(1);
        tagDto1.setName("@31");

        TagDto tagDto2 = new TagDto();
        tagDto2.setTagId(2);
        tagDto2.setName("");

        TagDto tagDto3 = new TagDto();
        tagDto3.setTagId(3);
        tagDto3.setName(null);

        return new Object[][]{{tagDto1}, {tagDto2}, {tagDto3}};
    }

    @ParameterizedTest
    @MethodSource("incorrectTag")
    void whenIsNotValidTagThenShouldThrowException(TagDto tagDto) {
        assertThrows(ValidationException.class, () -> TagValidator.isValidTag(tagDto));
    }

    public static Object[][] correctId() {
        return new Object[][]{{1}, {23}, {32424}};
    }

    @ParameterizedTest
    @MethodSource("correctId")
    void whenIsValidIdThenShouldNotThrowException(long id) {
        assertDoesNotThrow(() -> TagValidator.isValidId(id));
    }

    public static Object[][] incorrectId() {
        return new Object[][]{{-1}, {0}, {-4567887}};
    }

    @ParameterizedTest
    @MethodSource("incorrectId")
    void whenIsNotValidIdThenShouldThrowException(long id) {
        assertThrows(ValidationException.class, () -> TagValidator.isValidId(id));
    }
}