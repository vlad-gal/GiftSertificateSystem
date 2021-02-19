//package com.epam.esm.util;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class ParameterManagerTest {
//
//    @Test
//    void whenGiftCertificateQueryParametersProcessingThenShouldReturnMap() {
//        Map<String, String> queryParameters = new HashMap<>();
//        queryParameters.put("tagName", "поход,отдых");
//        queryParameters.put("name", "Cert Name");
//        queryParameters.put("description", "Cert Desc");
//        queryParameters.put("order", "-name");
//        queryParameters.put("page", "1");
//        queryParameters.put("per_page", "10");
//
//        Map<String, String> expected = new HashMap<>();
//        expected.put("tagName1", "поход");
//        expected.put("tagName2", "отдых");
//        expected.put("name", "Cert Name");
//        expected.put("description", "Cert Desc");
//        expected.put("order", "-name");
//        expected.put("page", "1");
//        expected.put("per_page", "10");
//
//        Map<String, String> processedMap = ParameterManager.giftCertificateQueryParametersProcessing(queryParameters);
//
//        assertEquals(expected, processedMap);
//    }
//
//    @Test
//    void whenDefaultQueryParametersProcessingThenShouldReturnMap() {
//        Map<String, String> queryParameters = new HashMap<>();
//        queryParameters.put("first_name", "John");
//        queryParameters.put("last_name", "Lukas");
//        queryParameters.put("login", "swQ");
//        queryParameters.put("order", "-first_name");
//        queryParameters.put("page", "1");
//        queryParameters.put("per_page", "10");
//
//        Map<String, String> processedMap = ParameterManager.defaultQueryParametersProcessing(queryParameters);
//
//        assertEquals(queryParameters, processedMap);
//    }
//}