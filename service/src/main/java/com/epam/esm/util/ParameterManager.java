package com.epam.esm.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ParameterManager {
    private final String REGEX_TAG_NAME = "tagName";
    private final String COMMA = ",";

    public Map<String, String> queryParametersProcessing(Map<String, String> queryParameters) {
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
        if (!queryParameters.containsKey("page")) {
            processedParameters.put("page", "1");
        }
        if (!queryParameters.containsKey("per_page")) {
            processedParameters.put("per_page", "10");
        }
        processedParameters.putAll(queryParameters);
        return processedParameters;
    }
}
