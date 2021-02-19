//package com.epam.esm.validator;
//
//import com.epam.esm.exception.ValidationException;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class BaseValidatorTest {
//
//    public static Object[][] correctId() {
//        return new Object[][]{{1}, {25}, {103}};
//    }
//
//    @ParameterizedTest
//    @MethodSource("correctId")
//    void whenIsValidIdThenShouldNotThrowException(long id) {
//        assertDoesNotThrow(() -> BaseValidator.isValidId(id));
//    }
//
//    public static Object[][] incorrectId() {
//        return new Object[][]{{0}, {-25}, {Long.MIN_VALUE}};
//    }
//
//    @ParameterizedTest
//    @MethodSource("incorrectId")
//    void whenIsNotValidIdThenShouldThrowException(long id) {
//        assertThrows(ValidationException.class, () -> BaseValidator.isValidId(id));
//    }
//}