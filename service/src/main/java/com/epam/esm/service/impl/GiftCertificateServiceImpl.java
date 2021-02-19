package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.dto.RequestGiftCertificateDto;
import com.epam.esm.dto.ResponseGiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ParameterManager;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.QueryParameterValidator;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ResponseGiftCertificateDto addGiftCertificate(RequestGiftCertificateDto giftCertificateDto) {
        Set<Tag> existTag = new HashSet<>();
        Set<Tag> newTag = new HashSet<>();
        if (giftCertificateDto.getTags() != null) {
            giftCertificateDto.getTags().stream().map(tagDto -> modelMapper.map(tagDto, Tag.class))
                    .forEach(tag -> {
                        if (checkIfTagAlreadyExist(tag)) {
                            log.debug("Tag already exist: {}", tag);
                            long tagId = tagRepository.findTagByName(tag.getName())
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
        long certificateId = giftCertificateRepository.save(giftCertificate).getId();
        giftCertificate.setId(certificateId);
        if (!existTag.isEmpty()) {
            giftCertificate.addAll(existTag);
            giftCertificateRepository.save(giftCertificate);
        }
        log.info("Gift certificate added: {}", giftCertificate);
        return modelMapper.map(giftCertificate, ResponseGiftCertificateDto.class);
    }

    @Override
    @Transactional
    public Set<TagDto> addTagToGiftCertificate(long giftCertificateId, TagDto tagDto) {
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(giftCertificateId);
        Tag tag = modelMapper.map(tagDto, Tag.class);
        if (checkIfTagAlreadyExist(tag)) {
            log.debug("Tag already exist: {}", tag);
            long tagId = tagRepository.findTagByName(tag.getName())
                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_NAME_NOT_FOUND,
                            tag.getName())).getTagId();
            tag.setTagId(tagId);
        }
        giftCertificate.add(tag);
        GiftCertificate updatedGiftCertificate = giftCertificateRepository.save(giftCertificate);
        log.info("Tag added to gift certificate: {}", tag);
        return updatedGiftCertificate.getTags()
                .stream().map(t -> modelMapper.map(t, TagDto.class)).collect(Collectors.toSet());
    }

    @Override
    public ResponseGiftCertificateDto findGiftCertificateById(long id) {
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(id);
        log.info("Found gift certificate by id: {}", giftCertificate);
        return modelMapper.map(giftCertificate, ResponseGiftCertificateDto.class);
    }

    @Override
    public Set<TagDto> findGiftCertificateTags(long certificateId) {
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(certificateId);
        Set<Tag> tags = giftCertificate.getTags();
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toSet());
    }

    @Override
    public List<ResponseGiftCertificateDto> findGiftCertificatesByParameters(Map<String, String> queryParameters,
                                                                             int page, int perPage) {
        QueryParameterValidator.isValidGiftCertificateQueryParameters(queryParameters);
        Predicate predicate = ParameterManager.createQPredicateForGiftCertificate(queryParameters);
        Sort sort = ParameterManager.createSortForGiftCertificate(queryParameters);
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<GiftCertificate> giftCertificates;
        if (predicate == null) {
            giftCertificates = giftCertificateRepository.findAll(pageable);
        } else {
            giftCertificates = giftCertificateRepository.findAll(predicate, pageable);
        }
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, ResponseGiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteGiftCertificateById(long id) {
        if (!orderRepository.findOrdersWhereGiftCertificateUsed(id).isEmpty()) {
            throw new DeleteResourceException(ExceptionPropertyKey.CANNOT_DELETE_GIFT_CERTIFICATE, id);
        }
        giftCertificateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteTagFromGiftCertificate(long certificateId, long tagId) {
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(certificateId);
        Tag tag = checkAndGetTag(tagId);
        giftCertificate.getTags().remove(tag);
        giftCertificateRepository.save(giftCertificate);
    }

    @Override
    @Transactional
    public ResponseGiftCertificateDto updateGiftCertificate(long giftCertificateId, RequestGiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(giftCertificateId);
        updateFields(giftCertificateDto, giftCertificate);
        giftCertificateRepository.save(giftCertificate);
        log.info("Gift certificate with id = {} updated", giftCertificateId);
        return modelMapper.map(checkAndGetGiftCertificate(giftCertificateId), ResponseGiftCertificateDto.class);
    }

    @Override
    @Transactional
    public ResponseGiftCertificateDto updateGiftCertificateField(long giftCertificateId, GiftCertificateField giftCertificateField) {
        GiftCertificateValidator.isValidField(giftCertificateField);
        GiftCertificate giftCertificate = checkAndGetGiftCertificate(giftCertificateId);
        updateField(giftCertificateField, giftCertificate);
        giftCertificateRepository.save(giftCertificate);
        log.info("Gift certificate with id = {} updated", giftCertificateId);
        return modelMapper.map(checkAndGetGiftCertificate(giftCertificateId), ResponseGiftCertificateDto.class);
    }

    private void updateFields(RequestGiftCertificateDto receivedGiftCertificate, GiftCertificate updatedGiftCertificate) {
        updatedGiftCertificate.setName(receivedGiftCertificate.getName());
        updatedGiftCertificate.setDescription(receivedGiftCertificate.getDescription());
        updatedGiftCertificate.setPrice(receivedGiftCertificate.getPrice());
        updatedGiftCertificate.setDuration(receivedGiftCertificate.getDuration());
        Set<Tag> giftCertificateTags = updatedGiftCertificate.getTags();
        if (receivedGiftCertificate.getTags() != null) {
            receivedGiftCertificate.getTags().stream().map(tagDto -> modelMapper.map(tagDto, Tag.class)).forEach(tag -> {
                if (!giftCertificateTags.contains(tag)) {
                    if (checkIfTagAlreadyExist(tag)) {
                        log.debug("Tag already exist: {}", tag);
                        long tagId = tagRepository.findTagByName(tag.getName())
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

    private boolean checkIfTagAlreadyExist(Tag tag) {
        return tagRepository.findAll().contains(tag);
    }

    private GiftCertificate checkAndGetGiftCertificate(long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository.findById(id);
        return giftCertificateOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.GIFT_CERTIFICATE_WITH_ID_NOT_FOUND, id));
    }

    private Tag checkAndGetTag(long id) {
        Optional<Tag> tagOptionalOptional = tagRepository.findById(id);
        return tagOptionalOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_ID_NOT_FOUND, id));
    }
}