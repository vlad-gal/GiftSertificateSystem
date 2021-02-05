package com.epam.esm.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryParameterManagerTest {

    public static Object[][] correctQueryParameter() {
        QueryParameter queryParameter1 =
                new QueryParameter("Hello1", "Cinema1", "Description1", "name", "asc");
        String query1 = "JOIN certificates_has_tags ON gift_certificates.certificateId = certificates_has_tags.certificateId " +
                "JOIN tags ON tags.tagId = certificates_has_tags.tagId WHERE tags.tagName LIKE '%Hello1%' AND gift_certificates.name " +
                "LIKE '%Cinema1%' AND gift_certificates.description LIKE '%Description1%' ORDER BY gift_certificates.name ASC";
        QueryParameter queryParameter2 =
                new QueryParameter("Hello2", "Cinema2", "Description2", "description", null);
        String query2 = "JOIN certificates_has_tags ON gift_certificates.certificateId = certificates_has_tags.certificateId " +
                "JOIN tags ON tags.tagId = certificates_has_tags.tagId WHERE tags.tagName LIKE '%Hello2%' AND gift_certificates.name " +
                "LIKE '%Cinema2%' AND gift_certificates.description LIKE '%Description2%' ORDER BY gift_certificates.description ";
        QueryParameter queryParameter3 =
                new QueryParameter("Hello3", "Cinema3", "Description3", null, null);
        String query3 = "JOIN certificates_has_tags ON gift_certificates.certificateId = certificates_has_tags.certificateId " +
                "JOIN tags ON tags.tagId = certificates_has_tags.tagId WHERE tags.tagName LIKE '%Hello3%' AND gift_certificates.name " +
                "LIKE '%Cinema3%' AND gift_certificates.description LIKE '%Description3%' ";
        QueryParameter queryParameter4 =
                new QueryParameter("Hello4", "Cinema4", null, null, null);
        String query4 = "JOIN certificates_has_tags ON gift_certificates.certificateId = certificates_has_tags.certificateId " +
                "JOIN tags ON tags.tagId = certificates_has_tags.tagId WHERE tags.tagName LIKE '%Hello4%' AND gift_certificates.name LIKE '%Cinema4%' ";
        QueryParameter queryParameter5 =
                new QueryParameter("Hello5", null, null, null, null);
        String query5 = "JOIN certificates_has_tags ON gift_certificates.certificateId = certificates_has_tags.certificateId " +
                "JOIN tags ON tags.tagId = certificates_has_tags.tagId WHERE tags.tagName LIKE '%Hello5%' ";
        QueryParameter queryParameter6 =
                new QueryParameter(null, null, null, null, null);
        String query6 = "";
        return new Object[][]{
                {queryParameter1, query1},
                {queryParameter2, query2},
                {queryParameter3, query3},
                {queryParameter4, query4},
                {queryParameter5, query5},
                {queryParameter6, query6}};
    }

    @ParameterizedTest
    @MethodSource("correctQueryParameter")
    void whenCreateQueryWithCorrectQueryParameterThenShouldReturnQuery(QueryParameter queryParameter, String query) {
        String actual = QueryParameterManager.createQuery(queryParameter);
        assertEquals(query, actual);
    }
}