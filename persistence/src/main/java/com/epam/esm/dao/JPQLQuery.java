package com.epam.esm.dao;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JPQLQuery {
    public final String SELECT_ALL_CERTIFICATES = "SELECT DISTINCT g FROM GiftCertificate g ";
    public final String DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM GiftCertificate WHERE id = ?1";
}
