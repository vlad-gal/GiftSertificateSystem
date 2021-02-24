package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ParameterManager;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TagServiceImplTest {
    private TagRepository tagRepository = mock(TagRepository.class);
    private ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    private TagService tagService = new TagServiceImpl(modelMapper, tagRepository);

    @Test
    void whenAddTagThenShouldReturnTagDto() {
        TagDto tagDto = new TagDto();
        tagDto.setName("Hi");

        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");

        when(tagRepository.save(modelMapper.map(tagDto, Tag.class))).thenReturn(tag);
        TagDto mockedTagDto = tagService.addTag(tagDto);
        assertEquals(tag, modelMapper.map(mockedTagDto, Tag.class));
    }

    @Test
    void whenFindAllTagsByParametersThenShouldReturnListTags() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("tagName", "Hi");
        Predicate predicate = ParameterManager.createQPredicateForTag(queryParameters);
        Pageable pageable = PageRequest.of(0, 2);
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        Page<Tag> tags = new PageImpl<>(Collections.singletonList(tag));

        when(tagRepository.findAll(predicate, pageable)).thenReturn(tags);
        List<TagDto> allTags = tagService.findAllTagsByParameters(queryParameters, 0, 2);
        assertEquals(1, allTags.size());
    }

    @Test
    void whenFindAllTagsWithoutParametersThenShouldReturnListTags() {
        Map<String, String> queryParameters = new HashMap<>();

        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        Page<Tag> tags = new PageImpl<>(Collections.singletonList(tag));

        when(tagRepository.findAll(any(Pageable.class))).thenReturn(tags);
        List<TagDto> allTags = tagService.findAllTagsByParameters(queryParameters, 0, 2);
        assertEquals(1, allTags.size());
    }

    @Test
    void whenAllTagsByParametersThenShouldThrowException() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("tagName", "H@@i");
        assertThrows(ValidationException.class, () -> tagService.findAllTagsByParameters(queryParameters, 0, 2));
    }

    @Test
    void whenFindTagByIdThenShouldReturnTagDto() {
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");

        when(tagRepository.findById(tag.getTagId())).thenReturn(Optional.of(tag));
        TagDto mockedTagDto = tagService.findTagById(tag.getTagId());

        assertEquals(tag, modelMapper.map(mockedTagDto, Tag.class));
    }

    @Test
    void whenFindTagByIdThenShouldThrowException() {
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(123));
    }

    @Test
    void whenDeleteTagByIdThenShouldNotThrowException() {
        long tagId = 1;
        doNothing().when(tagRepository).deleteById(tagId);

        assertDoesNotThrow(() -> tagService.deleteTagById(tagId));
    }
}