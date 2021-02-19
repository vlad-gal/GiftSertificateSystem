//package com.epam.esm.validator;
//
//import com.epam.esm.dto.UserDto;
//import com.epam.esm.exception.ValidationException;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class UserValidatorTest {
//
//    public static Object[][] correctUser() {
//        UserDto userDto1 = new UserDto();
//        userDto1.setFirstName("Гриша");
//        userDto1.setLastName("Яблуножко");
//        userDto1.setLogin("asssdqw");
//
//        UserDto userDto2 = new UserDto();
//        userDto2.setFirstName("John");
//        userDto2.setLastName("Doe");
//        userDto2.setLogin("jd");
//
//        return new Object[][]{{userDto1}, {userDto2}};
//    }
//
//    @ParameterizedTest
//    @MethodSource("correctUser")
//    void whenIsValidTagThenShouldNotThrowException(UserDto userDto) {
//        assertDoesNotThrow(() -> UserValidator.isValidUser(userDto));
//    }
//
//    public static Object[][] incorrectUser() {
//        UserDto userDto1 = new UserDto();
//        userDto1.setLogin("s@фывdd");
//
//        UserDto userDto2 = new UserDto();
//        userDto2.setFirstName(null);
//        userDto2.setLastName(null);
//        userDto2.setLogin(null);
//
//        return new Object[][]{{userDto1}, {userDto2}};
//    }
//
//    @ParameterizedTest
//    @MethodSource("incorrectUser")
//    void whenIsNotValidUserThenShouldThrowException(UserDto userDto) {
//        assertThrows(ValidationException.class, () -> UserValidator.isValidUser(userDto));
//    }
//}