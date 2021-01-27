package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.QueryParameter;

import java.util.List;
import java.util.Set;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    List<GiftCertificate> findCertificatesByQueryParameters(QueryParameter queryParameter);

//    Set<Tag> findGiftCertificateTags(long certificateId);

//    void addRelationBetweenTagAndGiftCertificate(long tagId, long giftCertificateId);
}