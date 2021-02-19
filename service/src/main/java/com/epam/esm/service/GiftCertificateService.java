package com.epam.esm.service;

import com.epam.esm.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GiftCertificateService {

    ResponseGiftCertificateDto addGiftCertificate(RequestGiftCertificateDto giftCertificateDto);

    Set<TagDto> addTagToGiftCertificate(long giftCertificateId, TagDto tagDto);

    ResponseGiftCertificateDto findGiftCertificateById(long id);

    List<ResponseGiftCertificateDto> findGiftCertificatesByParameters(Map<String, String> queryParameters, int page, int perPage);

    void deleteGiftCertificateById(long id);

    ResponseGiftCertificateDto updateGiftCertificate(long giftCertificateId, RequestGiftCertificateDto giftCertificateDto);

    Set<TagDto> findGiftCertificateTags(long certificateId);

    ResponseGiftCertificateDto updateGiftCertificateField(long giftCertificateId, GiftCertificateField giftCertificateField);

    void deleteTagFromGiftCertificate(long certificateId, long tagId);
}