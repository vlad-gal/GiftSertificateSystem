package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ParameterManager;
import com.epam.esm.validator.QueryParameterValidator;
import com.epam.esm.validator.TagValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TagServiceImpl implements TagService {
    private final ModelMapper modelMapper;
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(ModelMapper modelMapper, TagDao tagDao) {
        this.modelMapper = modelMapper;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public TagDto addTag(TagDto tagDto) {
        TagValidator.isValidTag(tagDto);
        Tag addedTag = modelMapper.map(tagDto, Tag.class);
        long tagId = tagDao.add(addedTag);
        addedTag.setTagId(tagId);
        log.info("Tag added: {}", addedTag);
        return modelMapper.map(addedTag, TagDto.class);
    }

    @Override
    @Transactional
    public List<TagDto> findAllTagsByParameters(Map<String, String> queryParameters) {
        Map<String, String> processedQueryParameters = ParameterManager.defaultQueryParametersProcessing(queryParameters);
        QueryParameterValidator.isValidTagQueryParameters(processedQueryParameters);
        List<Tag> tags = tagDao.findAllByParameters(processedQueryParameters);
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagDto findTagById(long tagId) {
        TagValidator.isValidId(tagId);
        Tag tag = retrieveTag(tagId);
        log.info("Found tag by id = {}, tag: {}", tagId, tag);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    @Transactional
    public void deleteTagById(long tagId) {
        TagValidator.isValidId(tagId);
        tagDao.removeById(tagId);
        log.info("Tag with id = {} deleted", tagId);
    }

    private Tag retrieveTag(long tagId) {
        Optional<Tag> optionalTag = tagDao.findById(tagId);
        return optionalTag.orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_ID_NOT_FOUND,
                tagId));
    }
}