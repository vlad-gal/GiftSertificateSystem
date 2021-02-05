package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.util.QueryParameter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GiftCertificateServiceImplTest {
    private GiftCertificateDao giftCertificateDao = mock(GiftCertificateDaoImpl.class);
    private TagDao tagDao = mock(TagDaoImpl.class);
    private ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    private GiftCertificateService giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, modelMapper, tagDao);


    @Test
    void whenAddGiftCertificateThenShouldReturnGiftCertificateDto() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("123"));
        giftCertificateDto.setDuration(1);
        giftCertificateDto.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificateDto.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        Set<TagDto> tagsDto = new HashSet<>();
        TagDto tagDto = new TagDto();
        tagDto.setName("Hi");
        tagsDto.add(tagDto);
        giftCertificateDto.setTags(tagsDto);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        tags.add(tag);
        giftCertificate.setTags(tags);

        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate.getId());
        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
        when(tagDao.findTagByName(tag.getName())).thenReturn(Optional.of(tag));

        GiftCertificateDto mockedGiftCertificateDto = giftCertificateService.addGiftCertificate(giftCertificateDto);
        giftCertificate.setCreatedDate(mockedGiftCertificateDto.getCreatedDate());
        giftCertificate.setLastUpdateDate(mockedGiftCertificateDto.getLastUpdateDate());
        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
    }

    @Test
    void whenAddGiftCertificateThenShouldThrowException() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("123"));
        giftCertificateDto.setDuration(1);
        giftCertificateDto.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificateDto.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        Set<TagDto> tagsDto = new HashSet<>();
        TagDto tagDto = new TagDto();
        tagDto.setName("Hi");
        tagsDto.add(tagDto);
        giftCertificateDto.setTags(tagsDto);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        tags.add(tag);
        giftCertificate.setTags(tags);

        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate.getId());
        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
        when(tagDao.findTagByName(tag.getName())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.addGiftCertificate(giftCertificateDto));
    }

    @Test
    void whenAddTagToGiftCertificateThenShouldReturnGiftCertificateDto() {
        TagDto tagDto = new TagDto();
        tagDto.setName("Hi");

        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        tags.add(tag);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setTags(tags);

        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        when(tagDao.findAll()).thenReturn(new LinkedList<>());
        when(tagDao.add(tag)).thenReturn(tag.getTagId());
        when(giftCertificateDao.update(giftCertificate)).thenReturn(giftCertificate);
        when(giftCertificateDao.findGiftCertificateTags(giftCertificate.getId())).thenReturn(tags);

        GiftCertificateDto mockedGiftCertificateDto = giftCertificateService.addTagToGiftCertificate(giftCertificate.getId(), tagDto);

        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
    }

    @Test
    void whenAddTagToGiftCertificateThenShouldThrowException() {
        TagDto tagDto = new TagDto();
        tagDto.setName("Hi");

        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));

        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
        when(tagDao.findTagByName(tag.getName())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.addTagToGiftCertificate(giftCertificate.getId(), tagDto));
    }

    @Test
    void whenFindGiftCertificateByIdThenShouldReturnGiftCertificate() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        tags.add(tag);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setTags(tags);

        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateDao.findGiftCertificateTags(giftCertificate.getId())).thenReturn(tags);
        GiftCertificateDto mockedGiftCertificateDto = giftCertificateService.findGiftCertificateById(giftCertificate.getId());

        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
    }

    @Test
    void whenFindGiftCertificateByIdThenShouldThrowException() {
        assertThrows(ValidationException.class, () -> giftCertificateService.findGiftCertificateById(-1));
    }

    @Test
    void whenFindGiftCertificatesByParametersThenShouldReturnListOfGiftCertificate() {
        QueryParameter parameter = new QueryParameter("Hi", null, null, null, null);

        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        tags.add(tag);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setTags(tags);

        when(giftCertificateDao.findCertificatesByQueryParameters(anyString())).thenReturn(Collections.singletonList(giftCertificate));

        List<GiftCertificateDto> giftCertificateDtos = giftCertificateService.findGiftCertificatesByParameters(parameter);

        assertEquals(Collections.singletonList(giftCertificate), giftCertificateDtos.stream()
                .map(giftCertificateDto -> modelMapper.map(giftCertificateDto, GiftCertificate.class))
                .collect(Collectors.toList()));
    }

    @Test
    void whenFindGiftCertificatesByParametersThenShouldThrowException() {
        QueryParameter parameter = new QueryParameter("@@#", null, null, null, null);

        assertThrows(ValidationException.class, () -> giftCertificateService.findGiftCertificatesByParameters(parameter));
    }

    @Test
    void whenDeleteGiftCertificateByIdThenShouldNotThrowException() {
        int certificateId = 1;
        doNothing().when(giftCertificateDao).removeById(certificateId);

        assertDoesNotThrow(() -> giftCertificateService.deleteGiftCertificateById(certificateId));
    }

    @Test
    void whenDeleteGiftCertificateByIdThenShouldThrowException() {
        int certificateId = -1;
        doNothing().when(giftCertificateDao).removeById(certificateId);

        assertThrows(ValidationException.class, () -> giftCertificateService.deleteGiftCertificateById(certificateId));
    }

    @Test
    void whenUpdateGiftCertificateThenShouldReturnUpdatedGiftCertificate() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        tags.add(tag);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setTags(tags);

        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("123"));
        giftCertificateDto.setDuration(1);
        giftCertificateDto.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificateDto.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        Set<TagDto> tagsDto = new HashSet<>();
        TagDto tagDto = new TagDto();
        tagDto.setName("Hi");
        tagsDto.add(tagDto);
        giftCertificateDto.setTags(tagsDto);

        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateDao.update(giftCertificate)).thenReturn(giftCertificate);
        when(tagDao.findAll()).thenReturn(new LinkedList<>());
        when(tagDao.add(tag)).thenReturn(tag.getTagId());
        when(giftCertificateDao.findGiftCertificateTags(giftCertificate.getId())).thenReturn(tags);
        GiftCertificateDto mockedGiftCertificateDto = giftCertificateService
                .updateGiftCertificate(giftCertificate.getId(), giftCertificateDto);

        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
    }

    @Test
    void whenUpdateGiftCertificateThenShouldReturnThrowException() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("123"));
        giftCertificateDto.setDuration(1);
        giftCertificateDto.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificateDto.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));

        assertThrows(ValidationException.class, () -> giftCertificateService.updateGiftCertificate(-123, giftCertificateDto));
    }
}