package com.epam.esm.validator;

import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class QueryParameterValidator {
    private final String REGEX_TAG_NAME_VALUE = "[а-яА-Я\\w\\s\\d\\.?!]{1,45}";
    private final String REGEX_TAG_NAME_KEY = "tagName([1-9]\\d*)?";
    private final String REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION_VALUE = "[а-яА-Я\\w\\s\\d\\.,?!]{1,250}";
    private final String REGEX_GIFT_CERTIFICATE_NAME_KEY = "name";
    private final String REGEX_GIFT_CERTIFICATE_DESCRIPTION_KEY = "description";
    private final String REGEX_ORDER_VALUE = "[-]?name|[-]?description";
    private final String REGEX_ORDER_KEY = "order";
    private final String REGEX_PAGE_KEY = "page";
    private final String REGEX_PER_PAGE_KEY = "per_page";
    private final String REGEX_PAGE_VALUE = "[1-9]\\d*";


//    private final String REGEX_DIRECTION = "asc|desc";

//    public void isValidQueryParameters(QueryParameterDto queryParameter) {
//        isValidTagName(queryParameter.getTagName());
//        isValidGiftCertificateName(queryParameter.getCertificateName());
//        isValidGiftCertificateDescription(queryParameter.getCertificateDescription());
//        isValidOrderType(queryParameter.getOrder());
//        isValidSortDirection(queryParameter.getDirection());
//    }

    public void isValidQueryParameters(Map<String,String> queryParameters) {
        queryParameters.forEach((key, value) ->{
            if (key.matches(REGEX_TAG_NAME_KEY)){
                isValidTagName(value);
            }
        });
        isValidGiftCertificateName(queryParameters.get(REGEX_GIFT_CERTIFICATE_NAME_KEY));
        isValidGiftCertificateDescription(queryParameters.get(REGEX_GIFT_CERTIFICATE_DESCRIPTION_KEY));
        isValidOrderType(queryParameters.get(REGEX_ORDER_KEY));
        isValidPage(queryParameters.get(REGEX_PAGE_KEY));
        isValidPage(queryParameters.get(REGEX_PER_PAGE_KEY));
    }

    private static void isValidPage(String page) {
        if (page != null && !page.isEmpty() && !page.matches(REGEX_PAGE_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_PAGE, page);
        }
    }


    private void isValidTagName(String tagName) {
        if (tagName != null && !tagName.isEmpty() && !tagName.matches(REGEX_TAG_NAME_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_TAG_NAME, tagName);
        }
    }

    private void isValidGiftCertificateName(String giftCertificateName) {
        if (giftCertificateName != null && !giftCertificateName.isEmpty()
                && !giftCertificateName.matches(REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_NAME, giftCertificateName);
        }
    }

    private void isValidGiftCertificateDescription(String giftCertificateDescription) {
        if (giftCertificateDescription != null && !giftCertificateDescription.isEmpty()
                && !giftCertificateDescription.matches(REGEX_GIFT_CERTIFICATE_NAME_AND_DESCRIPTION_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_GIFT_CERTIFICATE_DESCRIPTION,
                    giftCertificateDescription);
        }
    }

    private void isValidOrderType(String order) {
        System.out.println(order);
        if (order != null && !order.isEmpty() && !order.matches(REGEX_ORDER_VALUE)) {
            throw new ValidationException(ExceptionPropertyKey.INCORRECT_ORDER, order);
        }
    }
}