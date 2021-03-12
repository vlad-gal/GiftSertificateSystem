package com.epam.esm.util;

import com.epam.esm.entity.QGiftCertificate;
import com.epam.esm.entity.QTag;
import com.epam.esm.entity.QUser;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Map;

@UtilityClass
public class ParameterManager {
    private final String TAG_NAME = "tagName";
    private final String COMMA = ",";
    private final String NAME = "name";
    private final String ORDER = "order";
    private final char MINUS = '-';
    private final String DESCRIPTION = "description";
    private final String ID = "id";
    private final String TAG_ID = "tagId";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String LOGIN = "login";

    public Predicate createQPredicateForGiftCertificate(Map<String, String> queryParameters) {
        QPredicate predicate = QPredicate.builder();
        if (queryParameters.containsKey(TAG_NAME)) {
            Arrays.stream(queryParameters.get(TAG_NAME).split(COMMA))
                    .forEach(tagName -> predicate.add(tagName, name ->
                            QGiftCertificate.giftCertificate.tags.any().name.containsIgnoreCase(name)));
        }
        predicate.add(queryParameters.get(NAME), QGiftCertificate.giftCertificate.name::containsIgnoreCase);
        predicate.add(queryParameters.get(DESCRIPTION), QGiftCertificate.giftCertificate.description::containsIgnoreCase);
        return predicate.buildAnd();
    }

    public static Predicate createQPredicateForTag(Map<String, String> queryParameters) {
        QPredicate predicate = QPredicate.builder();
        predicate.add(queryParameters.get(TAG_NAME), QTag.tag.name::containsIgnoreCase);
        return predicate.buildAnd();
    }

    public static Predicate createQPredicateForUser(Map<String, String> queryParameters) {
        QPredicate predicate = QPredicate.builder();
        predicate.add(queryParameters.get(FIRST_NAME), QUser.user.firstName::containsIgnoreCase);
        predicate.add(queryParameters.get(LAST_NAME), QUser.user.lastName::containsIgnoreCase);
        predicate.add(queryParameters.get(LOGIN), QUser.user.login::containsIgnoreCase);
        return predicate.buildAnd();
    }

    public static Sort createSort(Map<String, String> queryParameters) {
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
            if (order.contains(ID)) {
                sort = Sort.by(TAG_ID);
            }
            if (order.contains(FIRST_NAME)) {
                sort = Sort.by(FIRST_NAME);
            }
            if (order.contains(LAST_NAME)) {
                sort = Sort.by(LAST_NAME);
            }
            if (order.contains(LOGIN)) {
                sort = Sort.by(LOGIN);
            }
            if (direction == MINUS && sort != null) {
                sort = sort.descending();
            }
            return sort;
        }
        return Sort.unsorted();
    }
}