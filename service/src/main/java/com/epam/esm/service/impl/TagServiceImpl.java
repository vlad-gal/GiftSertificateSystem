package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
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
    public TagDto addTag(TagDto tagDto) {
        TagValidator.isValidTag(tagDto);
        Tag addedTag = modelMapper.map(tagDto, Tag.class);
        long tagId = tagDao.add(addedTag);
        addedTag.setTagId(tagId);
        log.log(Level.INFO, "Tag added: {}", addedTag);
        return modelMapper.map(addedTag, TagDto.class);
    }

    @Override
    public Set<TagDto> findAllTags() {
        List<Tag> tags = tagDao.findAll();
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toSet());
    }

    @Override
    public TagDto findTagById(long tagId) {
        TagValidator.isValidId(tagId);
        Tag tag = retrieveTag(tagId);
        log.log(Level.INFO, "Found tag by id = {}, tag: {}", tagId, tag);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public void deleteTagById(long tagId) {
        TagValidator.isValidId(tagId);
        tagDao.removeById(tagId);
        log.log(Level.INFO, "Tag with id = {} deleted", tagId);
    }

    private Tag retrieveTag(long tagId) {
        Optional<Tag> optionalTag = tagDao.findById(tagId);
        return optionalTag.orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_ID_NOT_FOUND,
                tagId));
    }
}