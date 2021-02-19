package com.epam.esm.util;

import com.epam.esm.entity.QGiftCertificate;
import com.epam.esm.entity.QTag;
import com.epam.esm.entity.QUser;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ParameterManager {
    private final String REGEX_TAG_NAME = "tagName";
    private final String COMMA = ",";
    private final String NAME = "name";
    private final String ORDER = "order";
    private final char MINUS = '-';
    private final String DESCRIPTION = "description";

//    private final String PAGE = "page";
//    private final String PER_PAGE = "per_page";
//    private final String DEFAULT_PAGE = "0";
//    private final String DEFAULT_PER_PAGE = "10";
//
//    public Map<String, String> giftCertificateQueryParametersProcessing(Map<String, String> queryParameters) {
//        Map<String, String> processedParameters = new HashMap<>();
//        queryParameters.forEach((key, value) -> {
//            if (key.equalsIgnoreCase(REGEX_TAG_NAME)) {
//                if (value.contains(COMMA)) {
//                    String[] split = value.split(COMMA);
//                    int count = 1;
//                    for (String tag : split) {
//                        processedParameters.put(key + count++, tag);
//                    }
//                } else {
//                    processedParameters.put(key, value);
//                }
//            }
//        });
//        queryParameters.remove(REGEX_TAG_NAME);
////        createDefaultPageNumber(processedParameters);
//        processedParameters.putAll(queryParameters);
//        return processedParameters;
//    }

//    public Map<String, String> defaultQueryParametersProcessing(Map<String, String> queryParameters) {
////        createDefaultPageNumber(queryParameters);
//        return queryParameters;
//    }

    public Predicate createQPredicateForGiftCertificate(Map<String, String> queryParameters) {
        QPredicate predicate = QPredicate.builder();
        if (queryParameters.containsKey("tagName")) {
            Arrays.stream(queryParameters.get("tagName").split(","))
                    .forEach(tagName -> predicate.add(tagName, name -> QGiftCertificate.giftCertificate.tags.any().name.containsIgnoreCase(name)));
        }
        predicate.add(queryParameters.get("name"), QGiftCertificate.giftCertificate.name::containsIgnoreCase);
        predicate.add(queryParameters.get("description"), QGiftCertificate.giftCertificate.description::containsIgnoreCase);
        return predicate.buildAnd();
    }

    public static Sort createSortForGiftCertificate(Map<String, String> queryParameters) {
        if (queryParameters.containsKey(ORDER)) {
            Sort sort = null;
            String order = queryParameters.get(ORDER);
            char direction = order.charAt(0);
            if (order.contains(NAME)) {
                sort = Sort.by(NAME);
            }
            if (order.contains(DESCRIPTION)) {
                sort = Sort.by(DESCRIPTION);
            }
            if (direction == MINUS && sort != null) {
                sort = sort.descending();
            }
            return sort;
        }
        return Sort.unsorted();
    }

    public static Predicate createQPredicateForTag(Map<String, String> queryParameters) {
        QPredicate predicate = QPredicate.builder();
        predicate.add(queryParameters.get("tagName"), QTag.tag.name::containsIgnoreCase);
        return predicate.buildAnd();
    }

    public static Sort createSortForTag(Map<String, String> queryParameters) {
        if (queryParameters.containsKey(ORDER)) {
            Sort sort = null;
            String order = queryParameters.get(ORDER);
            char direction = order.charAt(0);
            if (order.contains(NAME)) {
                sort = Sort.by(NAME);
            }
            if (order.contains("id")) {
                sort = Sort.by("tagId");
            }
            if (direction == MINUS && sort != null) {
                sort = sort.descending();
            }
            return sort;
        }
        return Sort.unsorted();
    }

    public static Predicate createQPredicateForUser(Map<String, String> queryParameters) {
        QPredicate predicate = QPredicate.builder();
        predicate.add(queryParameters.get("firstName"), QUser.user.firstName::containsIgnoreCase);
        predicate.add(queryParameters.get("lastName"), QUser.user.lastName::containsIgnoreCase);
        predicate.add(queryParameters.get("login"), QUser.user.login::containsIgnoreCase);
        return predicate.buildAnd();
    }

    public static Sort createSortForUser(Map<String, String> queryParameters) {
        if (queryParameters.containsKey(ORDER)) {
            Sort sort = null;
            String order = queryParameters.get(ORDER);
            char direction = order.charAt(0);
            if (order.contains("firstName")) {
                sort = Sort.by("firstName");
            }
            if (order.contains("lastName")) {
                sort = Sort.by("lastName");
            }
            if (order.contains("login")) {
                sort = Sort.by("login");
            }
            if (direction == MINUS && sort != null) {
                sort = sort.descending();
            }
            return sort;
        }
        return Sort.unsorted();
    }

//    private void createDefaultPageNumber(Map<String, String> processedParameters) {
//        if (!processedParameters.containsKey(PAGE)) {
//            processedParameters.put(PAGE, DEFAULT_PAGE);
//        }
//        if (!processedParameters.containsKey(PER_PAGE)) {
//            processedParameters.put(PER_PAGE, DEFAULT_PER_PAGE);
//        }
//    }
}