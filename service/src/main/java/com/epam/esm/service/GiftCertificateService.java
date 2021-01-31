package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GiftCertificateService {

    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto);

    Set<TagDto> addTagToGiftCertificate(long giftCertificateId, TagDto tagDto);

    GiftCertificateDto findGiftCertificateById(long id);

    List<GiftCertificateDto> findGiftCertificatesByParameters(Map<String, String> queryParameters);

    void deleteGiftCertificateById(long id);

    GiftCertificateDto updateGiftCertificate(long giftCertificateId, GiftCertificateDto giftCertificateDto);

    Set<TagDto> findGiftCertificateTags(long certificateId);

    GiftCertificateDto updateGiftCertificateField(long giftCertificateId, GiftCertificateField giftCertificateField);

    void deleteTagFromGiftCertificate(long certificateId, long tagId);
}