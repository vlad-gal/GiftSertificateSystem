package com.epam.esm.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@UtilityClass
public class QueryManager {
    private final String WHERE = "WHERE ";
    private final String AND = "AND ";
    private final String OR = "OR ";
    private final String PERCENT_AND = "%' AND ";
    private final String PERCENT_OR = "%' OR ";
    private final String PERCENT = "%' ";
    private final String BLANK = "";
    private final String DESCRIPTION = "description";
    private final String DESC = "desc";
    private final String TAG_NAME_KEY = "tagName([1-9]\\d*)?";
    private final String FIRST_TAG_NAME = "tagName1";
    private final String TAG_NAME = "tagName";
    private final String NAME = "name";
    private final String ORDER = "order";
    private final char MINUS = '-';
    private final String PAGE = "page";
    private final String PER_PAGE = "per_page";
    private final String ID = "id";
    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME = "last_name";
    private final String LOGIN = "login";
    private final String JOIN_TAGS = "JOIN g.tags t ";
    private final String FIRST_NAME_LIKE = "u.firstName LIKE '%";
    private final String LAST_NAME_LIKE = "u.lastName LIKE '%";
    private final String LOGIN_LIKE = "u.login LIKE '%";
    private final String ORDER_BY_LOGIN = "ORDER BY u.login ";
    private final String ORDER_BY_FIRST_NAME = "ORDER BY u.firstName ";
    private final String ORDER_BY_LAST_NAME = "ORDER BY u.lastName ";
    private final String ORDER_BY_TAG_ID = "ORDER BY t.tagId ";
    private final String ORDER_BY_TAG_NAME = "ORDER BY t.name ";
    private final String GIFT_CERTIFICATES_NAME_LIKE = "g.name LIKE '%";
    private final String TAG_NAME_LIKE = "t.name LIKE '%";
    private final String GIFT_CERTIFICATES_DESCRIPTION_LIKE = "g.description LIKE '%";
    private final String ORDER_BY_GIFT_CERTIFICATE_NAME = "ORDER BY g.name ";
    private final String ORDER_BY_GIFT_CERTIFICATE_DESCRIPTION = "ORDER BY g.description ";

    public String createQueryForCertificates(Map<String, String> queryParameters) {
        removePageParameters(queryParameters);
        Map<String, String> params = new HashMap<>(queryParameters);
        StringBuilder query = new StringBuilder();
        if (queryParameters.containsKey(FIRST_TAG_NAME) || queryParameters.containsKey(TAG_NAME)) {
            query.append(JOIN_TAGS).append(WHERE);
            params.forEach((key, value) -> {
                if (key.matches(TAG_NAME_KEY)) {
                    query.append(TAG_NAME_LIKE).append(value).append(PERCENT_OR);
                    queryParameters.remove(key, value);
                }
            });
            if (queryParameters.isEmpty()) {
                query.replace(query.length() - OR.length(), query.length(), BLANK);
            } else {
                query.replace(query.length() - OR.length(), query.length(), AND);
            }
        } else if (!queryParameters.isEmpty()) {
            query.append(WHERE);
        }
        if (queryParameters.containsKey(NAME)) {
            query.append(GIFT_CERTIFICATES_NAME_LIKE).append(queryParameters.get(NAME)).append(PERCENT_AND);
        }
        if (queryParameters.containsKey(DESCRIPTION)) {
            query.append(GIFT_CERTIFICATES_DESCRIPTION_LIKE).append(queryParameters.get(DESCRIPTION)).append(PERCENT_AND);
        }
        if (query.toString().endsWith(AND)) {
            query.replace(query.length() - AND.length(), query.length(), BLANK);
        }
        if (query.toString().endsWith(WHERE)) {
            query.replace(query.length() - WHERE.length(), query.length(), BLANK);
        }
        if (queryParameters.containsKey(ORDER)) {
            String order = queryParameters.get(ORDER);
            char direction = order.charAt(0);
            if (order.contains(NAME)) {
                query.append(ORDER_BY_GIFT_CERTIFICATE_NAME);
            }
            if (order.contains(DESCRIPTION)) {
                query.append(ORDER_BY_GIFT_CERTIFICATE_DESCRIPTION);
            }
            if (direction == MINUS) {
                query.append(DESC);
            }
        }
        log.debug("Created query: {}", query);
        return query.toString();
    }

    public String createQueryForTags(Map<String, String> queryParameters) {
        removePageParameters(queryParameters);
        StringBuilder query = new StringBuilder();
        if (!queryParameters.isEmpty()) {
            query.append(WHERE);
        }
        if (queryParameters.containsKey(TAG_NAME)) {
            query.append(TAG_NAME_LIKE).append(queryParameters.get(TAG_NAME)).append(PERCENT);
        }
        if (query.toString().endsWith(WHERE)) {
            query.replace(query.length() - WHERE.length(), query.length(), BLANK);
        }
        if (queryParameters.containsKey(ORDER)) {
            String order = queryParameters.get(ORDER);
            char direction = order.charAt(0);
            if (order.contains(NAME)) {
                query.append(ORDER_BY_TAG_NAME);
            }
            if (order.contains(ID)) {
                query.append(ORDER_BY_TAG_ID);
            }
            if (direction == MINUS) {
                query.append(DESC);
            }
        }
        log.debug("Created query: {}", query);
        return query.toString();
    }

    public String createQueryForUsers(Map<String, String> queryParameters) {
        removePageParameters(queryParameters);
        StringBuilder query = new StringBuilder();
        if (!queryParameters.isEmpty()) {
            query.append(WHERE);
        }
        if (queryParameters.containsKey(FIRST_NAME)) {
            query.append(FIRST_NAME_LIKE).append(queryParameters.get(FIRST_NAME)).append(PERCENT_AND);
        }
        if (queryParameters.containsKey(LAST_NAME)) {
            query.append(LAST_NAME_LIKE).append(queryParameters.get(LAST_NAME)).append(PERCENT_AND);
        }
        if (queryParameters.containsKey(LOGIN)) {
            query.append(LOGIN_LIKE).append(queryParameters.get(LOGIN)).append(PERCENT_AND);
        }
        if (query.toString().endsWith(AND)) {
            query.replace(query.length() - AND.length(), query.length(), BLANK);
        }
        if (query.toString().endsWith(WHERE)) {
            query.replace(query.length() - WHERE.length(), query.length(), BLANK);
        }
        if (queryParameters.containsKey(ORDER)) {
            String order = queryParameters.get(ORDER);
            char direction = order.charAt(0);
            if (order.contains(FIRST_NAME)) {
                query.append(ORDER_BY_FIRST_NAME);
            }
            if (order.contains(LAST_NAME)) {
                query.append(ORDER_BY_LAST_NAME);
            }
            if (order.contains(LOGIN)) {
                query.append(ORDER_BY_LOGIN);
            }
            if (direction == MINUS) {
                query.append(DESC);
            }
        }
        log.debug("Created query: {}", query);
        return query.toString();
    }

    private Map<String, String> removePageParameters(Map<String, String> queryParameters) {
        queryParameters.remove(PAGE);
        queryParameters.remove(PER_PAGE);
        return queryParameters;
    }
}