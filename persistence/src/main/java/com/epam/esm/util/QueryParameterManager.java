package com.epam.esm.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class QueryParameterManager {
    private final String JOIN_CERTIFICATES_HAS_TAGS_AND_TAGS =
            "JOIN certificates_has_tags ON gift_certificates.certificateId = certificates_has_tags.certificateId " +
                    "JOIN tags ON tags.tagId = certificates_has_tags.tagId ";
    private final String WHERE = "WHERE ";
    private final String AND = "AND ";
    private final String PERCENT = "%' AND ";
    private final String GIFT_CERTIFICATE_NAME = "gift_certificates.name LIKE '%";
    private final String TAG_NAME = "tags.tagName LIKE '%";
    private final String GIFT_CERTIFICATE_DESCRIPTION = "gift_certificates.description LIKE '%";
    private final String ORDER_BY_GIFT_CERTIFICATE_NAME = "ORDER BY gift_certificates.name ";
    private final String ORDER_BY_GIFT_CERTIFICATE_DESCRIPTION = "ORDER BY gift_certificates.description ";
    private final String BLANK = "";
    private final String DESCRIPTION = "description";
    private final String ASC = "asc";
    private final String DESC = "desc";

    public String createQuery(QueryParameter queryParameter) {
        StringBuilder query = isParametersExist(queryParameter);
        if (queryParameter.getTagName() != null
                && !queryParameter.getTagName().isEmpty()) {
            query.append(TAG_NAME).append(queryParameter.getTagName()).append(PERCENT);
        }
        if (queryParameter.getCertificateName() != null
                && !queryParameter.getCertificateName().isEmpty()) {
            query.append(GIFT_CERTIFICATE_NAME)
                    .append(queryParameter.getCertificateName()).append(PERCENT);
        }
        if (queryParameter.getCertificateDescription() != null
                && !queryParameter.getCertificateDescription().isEmpty()) {
            query.append(GIFT_CERTIFICATE_DESCRIPTION)
                    .append(queryParameter.getCertificateDescription()).append(PERCENT);
        }
        if (query.toString().endsWith(AND)) {
            query.replace(query.length() - AND.length(), query.length(), BLANK);
        }
        if (queryParameter.getOrder() != null && !queryParameter.getOrder().isEmpty()) {
            if (queryParameter.getOrder().equals(DESCRIPTION)) {
                query.append(ORDER_BY_GIFT_CERTIFICATE_DESCRIPTION);
            } else {
                query.append(ORDER_BY_GIFT_CERTIFICATE_NAME);
            }
        }
        if (queryParameter.getDirection() != null && !queryParameter.getDirection().isEmpty()) {
            if (queryParameter.getDirection().equals(DESC)) {
                query.append(DESC.toUpperCase());
            } else {
                query.append(ASC.toUpperCase());
            }
        }
        log.debug("Created query: {}", query);
        return query.toString();
    }

    private StringBuilder isParametersExist(QueryParameter queryParameter) {
        StringBuilder query = new StringBuilder();
        if ((queryParameter.getTagName() == null || queryParameter.getTagName().isEmpty())) {
            if ((queryParameter.getCertificateName() == null || queryParameter.getCertificateName().isEmpty())
                    && (queryParameter.getCertificateDescription() == null || queryParameter.getCertificateDescription().isEmpty())) {
                return query;
            } else {
                return query.append(WHERE);
            }
        } else {
            return query.append(JOIN_CERTIFICATES_HAS_TAGS_AND_TAGS + WHERE);
        }
    }
}