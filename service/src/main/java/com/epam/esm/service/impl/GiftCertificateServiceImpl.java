package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ParameterManager;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.QueryParameterValidator;
import com.epam.esm.validator.TagValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, ModelMapper modelMapper, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.modelMapper = modelMapper;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional//+
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificateValidator.isValidGiftCertificate(giftCertificateDto);
        Set<Tag> existTag = new HashSet<>();
        Set<Tag> newTag = new HashSet<>();
        if (giftCertificateDto.getTags() != null) {
            giftCertificateDto.getTags().forEach(TagValidator::isValidTag);
            giftCertificateDto.getTags().stream().map(tagDto -> modelMapper.map(tagDto, Tag.class))
                    .forEach(tag -> {
                        if (checkIfTagAlreadyExist(tag)) {
                            log.debug("Tag already exist: {}", tag);
                            long tagId = tagDao.findTagByName(tag.getName())
                                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_NAME_NOT_FOUND,
                                            tag.getName())).getTagId();
                            tag.setTagId(tagId);
                            existTag.add(tag);
                        } else {
                            log.debug("New tag: {}", tag);
                            newTag.add(tag);
                        }
                    });
        }
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate.setTags(newTag);
        long certificateId = giftCertificateDao.add(giftCertificate);
        giftCertificate.setId(certificateId);
        if (!existTag.isEmpty()) {
            giftCertificate.addAll(existTag);
            giftCertificateDao.update(giftCertificate);
        }
        log.info("Gift certificate added: {}", giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    @Transactional//+
    public Set<TagDto> addTagToGiftCertificate(long giftCertificateId, TagDto tagDto) {
        GiftCertificateValidator.isValidId(giftCertificateId);
        TagValidator.isValidTag(tagDto);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(giftCertificateId);
        Tag tag = modelMapper.map(tagDto, Tag.class);
        if (checkIfTagAlreadyExist(tag)) {
            log.debug("Tag already exist: {}", tag);
            long tagId = tagDao.findTagByName(tag.getName())
                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_NAME_NOT_FOUND,
                            tag.getName())).getTagId();
            tag.setTagId(tagId);
        }
        giftCertificate.add(tag);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(giftCertificate);
        log.info("Tag added to gift certificate: {}", tag);
        return updatedGiftCertificate.getTags()
                .stream().map(t -> modelMapper.map(t, TagDto.class)).collect(Collectors.toSet());
    }

    @Override//+
    @Transactional
    public GiftCertificateDto findGiftCertificateById(long id) {
        GiftCertificateValidator.isValidId(id);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(id);
        log.info("Found gift certificate by id: {}", giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override//+
    @Transactional
    public Set<TagDto> findGiftCertificateTags(long certificateId) {
        GiftCertificateValidator.isValidId(certificateId);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(certificateId);
        Set<Tag> tags = giftCertificate.getTags();
        return tags.stream().map(t -> modelMapper.map(t, TagDto.class)).collect(Collectors.toSet());
    }

    @Override//-
    @Transactional
    public List<GiftCertificateDto> findGiftCertificatesByParameters(Map<String, String> queryParameters) {
        Map<String, String> processedQueryParameters = ParameterManager.queryParametersProcessing(queryParameters);
        QueryParameterValidator.isValidQueryParameters(processedQueryParameters);
        log.debug("Query parameter: {}", processedQueryParameters);
        List<GiftCertificate> giftCertificates = giftCertificateDao
                .findCertificatesByQueryParameters(processedQueryParameters);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }


//    @Override
//    @Transactional
//    public List<GiftCertificateDto> findGiftCertificatesByParameters(QueryParameterDto queryParameter, int limit, int offset) {
//        QueryParameterValidator.isValidQueryParameters(queryParameter);
//        log.debug("Query parameter: {}", queryParameter);
//        List<GiftCertificate> giftCertificates = giftCertificateDao
//                .findCertificatesByQueryParameters(modelMapper.map(queryParameter, QueryParameter.class), limit, offset);
//        return giftCertificates.stream()
//                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional//+
    public void deleteGiftCertificateById(long id) {
        GiftCertificateValidator.isValidId(id);
        giftCertificateDao.removeById(id);
    }

    @Override
    @Transactional//++
    public void deleteTagFromGiftCertificate(long certificateId, long tagId) {
        GiftCertificateValidator.isValidId(certificateId);
        TagValidator.isValidId(tagId);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(certificateId);
        Tag tag = checkAndGetTag(tagId);
        giftCertificate.getTags().remove(tag);
        giftCertificateDao.update(giftCertificate);
    }

    @Override
    @Transactional//+
    public GiftCertificateDto updateGiftCertificate(long giftCertificateId, GiftCertificateDto giftCertificateDto) {
        GiftCertificateValidator.isValidId(giftCertificateId);
        GiftCertificateValidator.isValidGiftCertificate(giftCertificateDto);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(giftCertificateId);
        updateFields(giftCertificateDto, giftCertificate);
        giftCertificateDao.update(giftCertificate);
        log.info("Gift certificate with id = {} updated", giftCertificateId);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }
//+
    private void updateFields(GiftCertificateDto receivedGiftCertificate, GiftCertificate updatedGiftCertificate) {
        updatedGiftCertificate.setName(receivedGiftCertificate.getName());
        updatedGiftCertificate.setDescription(receivedGiftCertificate.getDescription());
        updatedGiftCertificate.setPrice(receivedGiftCertificate.getPrice());
        updatedGiftCertificate.setDuration(receivedGiftCertificate.getDuration());
        if (receivedGiftCertificate.getTags() != null) {
            receivedGiftCertificate.getTags().forEach(TagValidator::isValidTag);
        }
        Set<Tag> giftCertificateTags = updatedGiftCertificate.getTags();
        if (receivedGiftCertificate.getTags() != null) {
            receivedGiftCertificate.getTags().stream().map(tagDto -> modelMapper.map(tagDto, Tag.class)).forEach(tag -> {
                if (!giftCertificateTags.contains(tag)) {
                    if (checkIfTagAlreadyExist(tag)) {
                        log.debug("Tag already exist: {}", tag);
                        long tagId = tagDao.findTagByName(tag.getName())
                                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_NAME_NOT_FOUND,
                                        tag.getName())).getTagId();
                        tag.setTagId(tagId);
                    }
                    giftCertificateTags.add(tag);
                }
            });
        }
        updatedGiftCertificate.setTags(giftCertificateTags);
        log.debug("Updated gift certificate: {}", updatedGiftCertificate);
    }

    @Override
    @Transactional//+
    public GiftCertificateDto updateGiftCertificateField(long giftCertificateId, GiftCertificateField giftCertificateField) {
        GiftCertificateValidator.isValidId(giftCertificateId);
        GiftCertificateValidator.isValidField(giftCertificateField);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(giftCertificateId);
        updateField(giftCertificateField, giftCertificate);
        giftCertificateDao.update(giftCertificate);
        log.info("Gift certificate with id = {} updated", giftCertificateId);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }
//+
    private void updateField(GiftCertificateField giftCertificateField, GiftCertificate updatedGiftCertificate) {
        GiftCertificateField.FieldName fieldName = GiftCertificateField.FieldName.valueOf(giftCertificateField.getFieldName().toUpperCase());
        switch (fieldName) {
            case NAME:
                updatedGiftCertificate.setName(giftCertificateField.getFieldValue());
                break;
            case DESCRIPTION:
                updatedGiftCertificate.setDescription(giftCertificateField.getFieldValue());
                break;
            case PRICE:
                updatedGiftCertificate.setPrice(new BigDecimal(giftCertificateField.getFieldValue()));
                break;
            case DURATION:
                updatedGiftCertificate.setDuration(Integer.parseInt(giftCertificateField.getFieldValue()));
                break;
        }
    }
//+
    private boolean checkIfTagAlreadyExist(Tag tag) {
        return tagDao.findAll().contains(tag);
    }
//+
    private GiftCertificate checkAndGetGiftCertificate(long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        return giftCertificateOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.GIFT_CERTIFICATE_WITH_ID_NOT_FOUND, id));
    }
//+
    private Tag checkAndGetTag(long id) {
        Optional<Tag> tagOptionalOptional = tagDao.findById(id);
        return tagOptionalOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_ID_NOT_FOUND, id));
    }
}