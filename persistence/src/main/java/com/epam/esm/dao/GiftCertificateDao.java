package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    List<GiftCertificate> findCertificatesByQueryParameters(Map<String, String> queryParameter);
//    List<GiftCertificate> findCertificatesByQueryParameters(QueryParameter queryParameter, int limit, int offset);

//    Set<Tag> findGiftCertificateTags(long certificateId);

//    void addRelationBetweenTagAndGiftCertificate(long tagId, long giftCertificateId);
}