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
    private final String GIFT_CERTIFICATES_NAME_LIKE = "g.name LIKE '%";
    private final String TAG_NAME_LIKE = "t.name LIKE '%";
    private final String GIFT_CERTIFICATES_DESCRIPTION_LIKE = "g.description LIKE '%";
    private final String ORDER_BY_GIFT_CERTIFICATE_NAME = "ORDER BY g.name ";
    private final String ORDER_BY_GIFT_CERTIFICATE_DESCRIPTION = "ORDER BY g.description ";
    private final String BLANK = "";
    private final String DESCRIPTION = "description";
    private final String DESC = "desc";
    private final String TAG_NAME_KEY = "tagName([1-9]\\d*)?";
    private final String FIRST_TAG_NAME = "tagName1";
    private final String TAG_NAME = "tagName";
    private final String JOIN_TAGS = "join g.tags t ";
    private final String NAME = "name";
    private final String ORDER = "order";
    private final char MINUS = '-';
    private final String PAGE = "page";
    private final String PER_PAGE = "per_page";

    public String createQueryForCertificates(Map<String, String> queryParameters) {
        queryParameters.remove(PAGE);
        queryParameters.remove(PER_PAGE);
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
}
