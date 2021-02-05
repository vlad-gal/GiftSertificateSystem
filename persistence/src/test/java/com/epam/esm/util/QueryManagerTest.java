package com.epam.esm.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryManagerTest {

    public static Object[][] queryParametersForGiftCertificate() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("tagName1", "поход");
        queryParameters1.put("tagName2", "отдых");
        queryParameters1.put("name", "Cert Name");
        queryParameters1.put("description", "Cert Desc");
        queryParameters1.put("order", "-name");
        queryParameters1.put("page", "1");
        queryParameters1.put("per_page", "10");
        String expectedQuery1 =
                "JOIN g.tags t WHERE t.name LIKE '%поход%' OR t.name LIKE '%отдых%' AND g.name LIKE '%Cert Name%' " +
                        "AND g.description LIKE '%Cert Desc%' ORDER BY g.name desc";
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("tagName1", "поход");
        queryParameters2.put("tagName2", "отдых");
        String expectedQuery2 = "JOIN g.tags t WHERE t.name LIKE '%поход%' OR t.name LIKE '%отдых%' ";
        Map<String, String> queryParameters3 = new HashMap<>();
        queryParameters3.put("name", "Cert Name");
        queryParameters3.put("description", "Cert Desc");
        queryParameters3.put("order", "description");
        String expectedQuery3 = "WHERE g.name LIKE '%Cert Name%' AND g.description LIKE '%Cert Desc%' ORDER BY g.description ";
        Map<String, String> queryParameters4 = new HashMap<>();
        queryParameters4.put("asdd", "Cert Name");
        queryParameters4.put("qwe12", "Cert Desc");
        queryParameters4.put("qwweee", "description");
        String expectedQuery4 = "";
        return new Object[][]{
                {queryParameters1, expectedQuery1},
                {queryParameters2, expectedQuery2},
                {queryParameters3, expectedQuery3},
                {queryParameters4, expectedQuery4}
        };
    }

    @ParameterizedTest
    @MethodSource("queryParametersForGiftCertificate")
    void whenCreateQueryForCertificatesThenShouldReturnQuery(Map<String, String> queryParameters, String expectedQuery) {
        String actual = QueryManager.createQueryForCertificates(queryParameters);
        assertEquals(expectedQuery, actual);
    }

    public static Object[][] queryParametersForTags() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("tagName", "поход");
        queryParameters1.put("order", "-name");
        queryParameters1.put("page", "1");
        queryParameters1.put("per_page", "10");
        String expectedQuery1 = "WHERE t.name LIKE '%поход%' ORDER BY t.name desc";
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("tagName1", "поход");
        queryParameters2.put("order", "id");
        String expectedQuery2 = "ORDER BY t.tagId ";
        return new Object[][]{
                {queryParameters1, expectedQuery1},
                {queryParameters2, expectedQuery2}
        };
    }

    @ParameterizedTest
    @MethodSource("queryParametersForTags")
    void whenCreateQueryForTagsThenShouldReturnQuery(Map<String, String> queryParameters, String expectedQuery) {
        String actual = QueryManager.createQueryForTags(queryParameters);
        assertEquals(expectedQuery, actual);
    }

    public static Object[][] queryParametersForUsers() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("first_name", "John");
        queryParameters1.put("last_name", "Lukas");
        queryParameters1.put("login", "swQ");
        queryParameters1.put("order", "-first_name");
        queryParameters1.put("page", "1");
        queryParameters1.put("per_page", "10");
        String expectedQuery1 =
                "WHERE u.firstName LIKE '%John%' AND u.lastName LIKE '%Lukas%' AND u.login LIKE '%swQ%' ORDER BY u.firstName desc";
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("login", "swQ");
        queryParameters2.put("order", "last_name");
        String expectedQuery2 = "WHERE u.login LIKE '%swQ%' ORDER BY u.lastName ";
        Map<String, String> queryParameters3 = new HashMap<>();
        queryParameters3.put("asdddqw", "swQ");
        queryParameters3.put("order", "login");
        String expectedQuery3 = "ORDER BY u.login ";
        Map<String, String> queryParameters4 = new HashMap<>();
        queryParameters4.put("asdddqw", "swQ");
        queryParameters4.put("orqweeder", "login");
        String expectedQuery4 = "";
        return new Object[][]{
                {queryParameters1, expectedQuery1},
                {queryParameters2, expectedQuery2},
                {queryParameters3, expectedQuery3},
                {queryParameters4, expectedQuery4}
        };
    }

    @ParameterizedTest
    @MethodSource("queryParametersForUsers")
    void whenCreateQueryForUsersThenShouldReturnQuery(Map<String, String> queryParameters, String expectedQuery) {
        String actual = QueryManager.createQueryForUsers(queryParameters);
        assertEquals(expectedQuery, actual);
    }
}