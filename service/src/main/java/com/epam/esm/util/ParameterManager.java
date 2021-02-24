package com.epam.esm.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ParameterManager {
    private final String REGEX_TAG_NAME = "tagName";
    private final String COMMA = ",";
    private final String PAGE = "page";
    private final String PER_PAGE = "per_page";
    private final String DEFAULT_PAGE = "1";
    private final String DEFAULT_PER_PAGE = "10";

    public Map<String, String> giftCertificateQueryParametersProcessing(Map<String, String> queryParameters) {
        Map<String, String> processedParameters = new HashMap<>();
        queryParameters.forEach((key, value) -> {
            if (key.equalsIgnoreCase(REGEX_TAG_NAME)) {
                if (value.contains(COMMA)) {
                    String[] split = value.split(COMMA);
                    int count = 1;
                    for (String tag : split) {
                        processedParameters.put(key + count++, tag);
                    }
                } else {
                    processedParameters.put(key, value);
                }
            }
        });
        queryParameters.remove(REGEX_TAG_NAME);
        createDefaultPageNumber(processedParameters);
        processedParameters.putAll(queryParameters);
        return processedParameters;
    }

    public Map<String, String> defaultQueryParametersProcessing(Map<String, String> queryParameters) {
        createDefaultPageNumber(queryParameters);
        return queryParameters;
    }

    private void createDefaultPageNumber(Map<String, String> processedParameters) {
        if (!processedParameters.containsKey(PAGE)) {
            processedParameters.put(PAGE, DEFAULT_PAGE);
        }
        if (!processedParameters.containsKey(PER_PAGE)) {
            processedParameters.put(PER_PAGE, DEFAULT_PER_PAGE);
        }
    }
}