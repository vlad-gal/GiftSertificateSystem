package com.epam.esm.util;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParameterManagerTest {

    @Test
    void whenCreateQPredicateForGiftCertificateThenShouldPredicate() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("tagName", "поход,отдых");
        queryParameters.put("name", "Cert Name");
        queryParameters.put("description", "Cert Desc");

        Predicate actual = ParameterManager.createQPredicateForGiftCertificate(queryParameters);

        assertNotNull(actual);
    }

    @Test
    void whenCreateQPredicateForTagThenShouldPredicate() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("tagName", "поход");

        Predicate actual = ParameterManager.createQPredicateForTag(queryParameters);

        assertNotNull(actual);
    }

    @Test
    void whenCreateQPredicateForUserThenShouldPredicate() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("firstName", "John");
        queryParameters.put("lastName", "Lukas");
        queryParameters.put("login", "swQ");

        Predicate actual = ParameterManager.createQPredicateForUser(queryParameters);

        assertNotNull(actual);
    }

    public static Object[][] sortQueryParameters() {
        Map<String, String> queryParameters1 = new HashMap<>();
        queryParameters1.put("order", "-firstName");
        Sort sort1 = Sort.by(Sort.Direction.DESC, "firstName");
        Map<String, String> queryParameters2 = new HashMap<>();
        queryParameters2.put("order", "lastName");
        Sort sort2 = Sort.by("lastName");
        Map<String, String> queryParameters3 = new HashMap<>();
        queryParameters3.put("order", "login");
        Sort sort3 = Sort.by("login");
        Map<String, String> queryParameters4 = new HashMap<>();
        queryParameters4.put("order", "-id");
        Sort sort4 = Sort.by(Sort.Direction.DESC, "tagId");
        Map<String, String> queryParameters5 = new HashMap<>();
        queryParameters5.put("order", "description");
        Sort sort5 = Sort.by("description");
        Map<String, String> queryParameters6 = new HashMap<>();
        queryParameters6.put("order", "-name");
        Sort sort6 = Sort.by(Sort.Direction.DESC, "name");
        Map<String, String> queryParameters7 = new HashMap<>();
        queryParameters7.put("123123", "-name");
        Sort sort7 = Sort.unsorted();
        return new Object[][]{
                {queryParameters1, sort1},
                {queryParameters2, sort2},
                {queryParameters3, sort3},
                {queryParameters4, sort4},
                {queryParameters5, sort5},
                {queryParameters6, sort6},
                {queryParameters7, sort7}
        };
    }

    @ParameterizedTest
    @MethodSource("sortQueryParameters")
    void whenCreateSortThenShouldReturnSort(Map<String, String> queryParameters, Sort expected) {
        Sort actual = ParameterManager.createSort(queryParameters);
        assertEquals(expected, actual);
    }
}