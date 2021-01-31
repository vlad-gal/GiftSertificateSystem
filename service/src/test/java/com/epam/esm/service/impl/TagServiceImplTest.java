//package com.epam.esm.service.impl;
//
//import com.epam.esm.dao.TagDao;
//import com.epam.esm.dao.impl.TagDaoImpl;
//import com.epam.esm.dto.TagDto;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exception.ResourceNotFoundException;
//import com.epam.esm.exception.ValidationException;
//import com.epam.esm.service.TagService;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.config.Configuration;
//import org.modelmapper.convention.MatchingStrategies;
//
//import java.util.Collections;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//class TagServiceImplTest {
//    private TagDao tagDao = mock(TagDaoImpl.class);
//    private ModelMapper modelMapper = new ModelMapper();
//
//    {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
//                .setFieldMatchingEnabled(true)
//                .setSkipNullEnabled(true)
//                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
//    }
//
//    private TagService tagService = new TagServiceImpl(modelMapper, tagDao);
//
//
//    @Test
//    void whenAddTagThenShouldReturnTagDto() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Hi");
//
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//
//        when(tagDao.add(modelMapper.map(tagDto, Tag.class))).thenReturn(tag.getTagId());
//        TagDto mockedTagDto = tagService.addTag(tagDto);
//        assertEquals(tag, modelMapper.map(mockedTagDto, Tag.class));
//    }
//
//    @Test
//    void whenAddTagThenShouldThrowException() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("@3e12");
//
//        assertThrows(ValidationException.class, () -> tagService.addTag(tagDto));
//    }
//
//    @Test
//    void whenFindAllTagsThenShouldReturnSetTags() {
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//
//        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
//        Set<TagDto> allTags = tagService.findAllTags();
//        assertEquals(1, allTags.size());
//    }
//
//    @Test
//    void whenFindTagByIdThenShouldReturnTagDto() {
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//
//        when(tagDao.findById(tag.getTagId())).thenReturn(Optional.of(tag));
//        TagDto mockedTagDto = tagService.findTagById(tag.getTagId());
//
//        assertEquals(tag, modelMapper.map(mockedTagDto, Tag.class));
//    }
//
//    @Test
//    void whenFindTagByIdThenShouldThrowException() {
//        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(123));
//    }
//
//    @Test
//    void whenDeleteTagByIdThenShouldNotThrowException() {
//        int tagId = 1;
//        doNothing().when(tagDao).removeById(tagId);
//
//        assertDoesNotThrow(() -> tagService.deleteTagById(tagId));
//    }
//
//    @Test
//    void whenDeleteTagByIdThenShouldThrowException() {
//        int tagId = -1;
//        doNothing().when(tagDao).removeById(tagId);
//
//        assertThrows(ValidationException.class, () -> tagService.deleteTagById(tagId));
//    }
//}