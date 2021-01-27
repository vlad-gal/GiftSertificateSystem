package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.QueryParameter;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.QueryParameterValidator;
import com.epam.esm.validator.TagValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Override//++
    @Transactional
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificateValidator.isValidGiftCertificate(giftCertificateDto);
//        Set<Tag> existTag = new HashSet<>();
//        Set<Tag> newTag = new HashSet<>();
//        if (giftCertificateDto.getTags() != null) {
//            giftCertificateDto.getTags().forEach(TagValidator::isValidTag);
//            giftCertificateDto.getTags().stream().map(tagDto -> modelMapper.map(tagDto, Tag.class))
//                    .forEach(tag -> {
//                        if (checkIfTagAlreadyExist(tag)) {
//                            log.debug("Tag already exist: {}", tag);
//                            long tagId = tagDao.findTagByName(tag.getName())
//                                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_NAME_NOT_FOUND,
//                                            tag.getName())).getTagId();
//                            tag.setTagId(tagId);
//                            existTag.add(tag);
//                        } else {
//                            log.debug("New tag: {}", tag);
//                            newTag.add(tag);
//                        }
//                    });
//        }
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
//        giftCertificate.setTags(newTag);
        long certificateId = giftCertificateDao.add(giftCertificate);
        giftCertificate.setId(certificateId);
//        if (!existTag.isEmpty()) {
//            giftCertificate.addAll(existTag);
//            giftCertificateDao.update(giftCertificate);
//        }
        log.info("Gift certificate added: {}", giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override//++
    @Transactional
    public GiftCertificateDto addTagToGiftCertificate(long giftCertificateId, TagDto tagDto) {
        GiftCertificateValidator.isValidId(giftCertificateId);
        TagValidator.isValidTag(tagDto);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(giftCertificateId);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        Tag tag = modelMapper.map(tagDto, Tag.class);
//        checkAndAddRelationBetweenTagAndGiftCertificate(giftCertificateId, tag);
        if (checkIfTagAlreadyExist(tag)) {
            log.debug("Tag already exist: {}", tag);
            long tagId = tagDao.findTagByName(tag.getName())
                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_NAME_NOT_FOUND,
                            tag.getName())).getTagId();
            tag.setTagId(tagId);
        }
        giftCertificate.add(tag);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(giftCertificate);
//        Set<Tag> giftCertificateTags = giftCertificateDao.findGiftCertificateTags(giftCertificateId);
//        updatedGiftCertificate.setTags(giftCertificateTags);
        log.info("Tag added to gift certificate: {}", tag);
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    @Override//++
    @Transactional
    public GiftCertificateDto findGiftCertificateById(long id) {
        GiftCertificateValidator.isValidId(id);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(id);
//        giftCertificate.setTags(giftCertificateDao.findGiftCertificateTags(id));
        log.info("Found gift certificate by id: {}", giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findGiftCertificatesByParameters(QueryParameterDto queryParameter) {
        QueryParameterValidator.isValidQueryParameters(queryParameter);
        log.debug("Query parameter: {}", queryParameter);
        List<GiftCertificate> giftCertificates = giftCertificateDao
                .findCertificatesByQueryParameters(modelMapper.map(queryParameter, QueryParameter.class));
//        for (GiftCertificate certificate : giftCertificates) {
//            Set<Tag> giftCertificateTags = giftCertificateDao.findGiftCertificateTags(certificate.getId());
//            certificate.setTags(giftCertificateTags);
//        }
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    //++
    @Override
    @Transactional
    public void deleteGiftCertificateById(long id) {
        GiftCertificateValidator.isValidId(id);
        giftCertificateDao.removeById(id);
    }

    //++
    @Override
    @Transactional
    public GiftCertificateDto updateGiftCertificate(long giftCertificateId, GiftCertificateDto giftCertificateDto) {
        GiftCertificateValidator.isValidId(giftCertificateId);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(giftCertificateId);
        updateFields(giftCertificateDto, giftCertificate);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDao.update(giftCertificate);
        log.info("Gift certificate with id = {} updated", giftCertificateId);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    private void checkAndAddRelationBetweenTagAndGiftCertificate(long giftCertificateId, Tag tag) {
        Tag processedTag;
        if (checkIfTagAlreadyExist(tag)) {
            log.debug("Tag already exist: {}", tag);
            processedTag = tagDao.findTagByName(tag.getName())
                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_NAME_NOT_FOUND,
                            tag.getName()));
        } else {
            log.debug("New tag: {}", tag);
            long tagId = tagDao.add(tag);
            processedTag = tag;
            processedTag.setTagId(tagId);
        }
        tag.setTagId(processedTag.getTagId());
//        giftCertificateDao.addRelationBetweenTagAndGiftCertificate(processedTag.getTagId(), giftCertificateId);
    }

    //++
    private void updateFields(GiftCertificateDto receivedGiftCertificate, GiftCertificate updatedGiftCertificate) {
        if (receivedGiftCertificate.getName() != null && !receivedGiftCertificate.getName().isEmpty()) {
            updatedGiftCertificate.setName(receivedGiftCertificate.getName());
        }
        if (receivedGiftCertificate.getDescription() != null && !receivedGiftCertificate.getDescription().isEmpty()) {
            updatedGiftCertificate.setDescription(receivedGiftCertificate.getDescription());
        }
        if (receivedGiftCertificate.getPrice() != null) {
            updatedGiftCertificate.setPrice(receivedGiftCertificate.getPrice());
        }
        if (receivedGiftCertificate.getDuration() > 0) {
            updatedGiftCertificate.setDuration(receivedGiftCertificate.getDuration());
        }
        GiftCertificateValidator.isValidGiftCertificate(modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class));
//        if (receivedGiftCertificate.getTags() != null) {
//            receivedGiftCertificate.getTags().forEach(TagValidator::isValidTag);
//        }
        Set<Tag> giftCertificateTags = updatedGiftCertificate.getTags();

//        if (receivedGiftCertificate.getTags() != null) {
//            receivedGiftCertificate.getTags().stream().map(tagDto -> modelMapper.map(tagDto, Tag.class)).forEach(tag -> {
//                if (!giftCertificateTags.contains(tag)) {
//                    if (checkIfTagAlreadyExist(tag)) {
//                        log.debug("Tag already exist: {}", tag);
//                        long tagId = tagDao.findTagByName(tag.getName())
//                                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_NAME_NOT_FOUND,
//                                        tag.getName())).getTagId();
//                        tag.setTagId(tagId);
//                    }
//                    giftCertificateTags.add(tag);
//                }
//            });
//        }
        updatedGiftCertificate.setTags(giftCertificateTags);
        log.debug("Updated gift certificate: {}", updatedGiftCertificate);
    }

    //++
    private boolean checkIfTagAlreadyExist(Tag tag) {
        return tagDao.findAll().contains(tag);
    }

    //++
    private GiftCertificate checkAndGetGiftCertificate(long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        return giftCertificateOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.GIFT_CERTIFICATE_WITH_ID_NOT_FOUND, id));
    }
}