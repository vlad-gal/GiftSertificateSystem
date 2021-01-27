package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.util.QueryParameter;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto addTagToGiftCertificate(long giftCertificateId, TagDto tagDto);

    GiftCertificateDto findGiftCertificateById(long id);

    List<GiftCertificateDto> findGiftCertificatesByParameters(QueryParameterDto queryParameter);

    void deleteGiftCertificateById(long id);

    GiftCertificateDto updateGiftCertificate(long giftCertificateId, GiftCertificateDto giftCertificateDto);
}