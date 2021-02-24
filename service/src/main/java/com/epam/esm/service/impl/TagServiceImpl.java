package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ParameterManager;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public TagDto addTag(TagDto tagDto) {
        Tag addedTag = tagRepository.save(modelMapper.map(tagDto, Tag.class));
        log.info("Tag added: {}", addedTag);
        return modelMapper.map(addedTag, TagDto.class);
    }

    @Override
    public List<TagDto> findAllTagsByParameters(Map<String, String> queryParameters, int page, int perPage) {
        QueryParameterValidator.isValidTagQueryParameters(queryParameters);
        Predicate predicate = ParameterManager.createQPredicateForTag(queryParameters);
        Sort sort = ParameterManager.createSort(queryParameters);
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<Tag> tags;
        if (predicate == null) {
            tags = tagRepository.findAll(pageable);
        } else {
            tags = tagRepository.findAll(predicate, pageable);
        }
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public TagDto findTagById(long tagId) {
        Tag tag = retrieveTag(tagId);
        log.info("Found tag by id = {}, tag: {}", tagId, tag);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    @Transactional
    public void deleteTagById(long tagId) {
        tagRepository.deleteById(tagId);
        log.info("Tag with id = {} deleted", tagId);
    }

    private Tag retrieveTag(long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        return optionalTag.orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_ID_NOT_FOUND,
                tagId));
    }
}