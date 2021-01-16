package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.QueryParameter;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto addTagToGiftCertificate(long giftCertificateId, TagDto tagDto);

    GiftCertificateDto findGiftCertificateById(long id);

    List<GiftCertificateDto> findGiftCertificatesByParameters(QueryParameter queryParameter);

    void deleteGiftCertificateById(long id);

    GiftCertificateDto updateGiftCertificate(long giftCertificateId, GiftCertificateDto giftCertificateDto);
}