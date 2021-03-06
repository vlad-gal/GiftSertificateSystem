package com.epam.esm.validator;

import com.epam.esm.exception.ValidationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryParameterValidatorTest {

    public static Object[][] correctQueryParametersForGiftCertificate() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("tagName", "поход,отдых");
        queryParameters1.put("name", "Cert Name");
        queryParameters1.put("description", "Cert Desc");
        queryParameters1.put("order", "-name");
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("tagName", "поход,отдых");
        Map<String, String> queryParameters3 = new HashMap<>();
        queryParameters3.put("name", "Cert Name");
        queryParameters3.put("description", "Cert Desc");
        queryParameters3.put("order", "description");
        Map<String, String> queryParameters4 = new HashMap<>();
        queryParameters4.put("asdd", "Cert Name");
        queryParameters4.put("qwe12", "Cert Desc");
        queryParameters4.put("qwweee", "description");
        return new Object[][]{
                {queryParameters1},
                {queryParameters2},
                {queryParameters3},
                {queryParameters4}
        };
    }

    @ParameterizedTest
    @MethodSource("correctQueryParametersForGiftCertificate")
    void whenIsValidGiftCertificateQueryParametersThenShouldNotThrowException(Map<String, String> queryParameters) {
        assertDoesNotThrow(() -> QueryParameterValidator.isValidGiftCertificateQueryParameters(queryParameters));
    }

    public static Object[][] incorrectQueryParametersForGiftCertificate() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("tagName", "пох@од");
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("name", "Cert N#$$#ame");
        Map<String, String> queryParameters3 = new HashMap<>();
        queryParameters3.put("description", "Cer#$#$t Desc");
        Map<String, String> queryParameters4 = new HashMap<>();
        queryParameters4.put("order", "asddqe");
        return new Object[][]{
                {queryParameters1},
                {queryParameters2},
                {queryParameters3},
                {queryParameters4}
        };
    }

    @ParameterizedTest
    @MethodSource("incorrectQueryParametersForGiftCertificate")
    void whenIsNotValidGiftCertificateQueryParametersThenShouldThrowException(Map<String, String> queryParameters) {
        assertThrows(ValidationException.class, () -> QueryParameterValidator.isValidGiftCertificateQueryParameters(queryParameters));
    }

    public static Object[][] correctQueryParametersForTag() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("tagName", "поход");
        queryParameters1.put("order", "-name");
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("tagName1", "поход");
        queryParameters2.put("order", "id");
        return new Object[][]{
                {queryParameters1},
                {queryParameters2}
        };
    }

    @ParameterizedTest
    @MethodSource("correctQueryParametersForTag")
    void whenIsValidTagQueryParametersThenShouldNotThrowException(Map<String, String> queryParameters) {
        assertDoesNotThrow(() -> QueryParameterValidator.isValidTagQueryParameters(queryParameters));
    }

    public static Object[][] incorrectQueryParametersForTag() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("tagName", "п@#!оход");
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("order", "qweid");
        return new Object[][]{
                {queryParameters1},
                {queryParameters2}
        };
    }

    @ParameterizedTest
    @MethodSource("incorrectQueryParametersForTag")
    void whenIsNotValidTagQueryParametersThenShouldThrowException(Map<String, String> queryParameters) {
        assertThrows(ValidationException.class, () -> QueryParameterValidator.isValidTagQueryParameters(queryParameters));
    }

    public static Object[][] correctQueryParametersForUser() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("firstName", "John");
        queryParameters1.put("lastName", "Lukas");
        queryParameters1.put("login", "swQ");
        queryParameters1.put("order", "-firstName");
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("login", "swQ");
        queryParameters2.put("order", "lastName");
        Map<String, String> queryParameters3 = new HashMap<>();
        queryParameters3.put("asdddqw", "swQ");
        queryParameters3.put("order", "login");
        Map<String, String> queryParameters4 = new HashMap<>();
        queryParameters4.put("asdddqw", "swQ");
        queryParameters4.put("orqweeder", "login");
        return new Object[][]{
                {queryParameters1},
                {queryParameters2},
                {queryParameters3},
                {queryParameters4}
        };
    }

    @ParameterizedTest
    @MethodSource("correctQueryParametersForUser")
    void whenIsValidUserQueryParametersThenShouldNotThrowException(Map<String, String> queryParameters) {
        assertDoesNotThrow(() -> QueryParameterValidator.isValidUserQueryParameters(queryParameters));
    }

    public static Object[][] incorrectQueryParametersForUser() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("firstName", "Joh@n");
        queryParameters1.put("order", "-first_name");
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("lastName", "Lu@as");
        Map<String, String> queryParameters3 = new HashMap<>();
        queryParameters3.put("login", "swQw233аываФQ");
        Map<String, String> queryParameters4 = new HashMap<>();
        queryParameters4.put("order", "-ФЦУ_name");
        return new Object[][]{
                {queryParameters1},
                {queryParameters2},
                {queryParameters3},
                {queryParameters4}
        };
    }

    @ParameterizedTest
    @MethodSource("incorrectQueryParametersForUser")
    void whenIsNotValidUserQueryParametersThenShouldThrowException(Map<String, String> queryParameters) {
        assertThrows(ValidationException.class, () -> QueryParameterValidator.isValidUserQueryParameters(queryParameters));
    }
}