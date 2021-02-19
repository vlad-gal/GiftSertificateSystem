//package com.epam.esm.service.impl;
//
//import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
//import com.epam.esm.dao.impl.OrderDaoImpl;
//import com.epam.esm.dao.impl.TagDaoImpl;
//import com.epam.esm.dto.GiftCertificateDto;
//import com.epam.esm.dto.GiftCertificateField;
//import com.epam.esm.dto.TagDto;
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exception.DeleteResourceException;
//import com.epam.esm.exception.ResourceNotFoundException;
//import com.epam.esm.exception.ValidationException;
//import com.epam.esm.service.GiftCertificateService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.config.Configuration;
//import org.modelmapper.convention.MatchingStrategies;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class GiftCertificateServiceImplTest {
//    private GiftCertificateDao giftCertificateDao = mock(GiftCertificateDaoImpl.class);
//    private TagDao tagDao = mock(TagDaoImpl.class);
//    private OrderDao orderDao = mock(OrderDaoImpl.class);
//    private ModelMapper modelMapper = new ModelMapper();
//
//    {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
//                .setFieldMatchingEnabled(true)
//                .setSkipNullEnabled(true)
//                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
//    }
//
//    private GiftCertificateService giftCertificateService =
//            new GiftCertificateServiceImpl(giftCertificateDao, tagDao, orderDao, modelMapper);
//
//
//    @Test
//    void whenAddGiftCertificateThenShouldReturnGiftCertificateDto() {
//        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
//        giftCertificateDto.setName("Hello");
//        giftCertificateDto.setDescription("Hello from description");
//        giftCertificateDto.setPrice(new BigDecimal("123"));
//        giftCertificateDto.setDuration(1);
//        Set<TagDto> tagsDto = new HashSet<>();
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Hi");
//        tagsDto.add(tagDto);
//        TagDto newTagDto = new TagDto();
//        newTagDto.setName("New TAG");
//        tagsDto.add(newTagDto);
//        giftCertificateDto.setTags(tagsDto);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//        Tag newTag = new Tag();
//        newTag.setName("New TAG");
//        tags.add(newTag);
//        giftCertificate.setTags(tags);
//
//        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
//        when(tagDao.findTagByName(tag.getName())).thenReturn(Optional.of(tag));
//        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate.getId());
//
//        GiftCertificateDto mockedGiftCertificateDto = giftCertificateService.addGiftCertificate(giftCertificateDto);
//        giftCertificate.setCreatedDate(mockedGiftCertificateDto.getCreatedDate());
//        giftCertificate.setLastUpdateDate(mockedGiftCertificateDto.getLastUpdateDate());
//        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
//    }
//
//    @Test
//    void whenAddGiftCertificateThenShouldThrowException() {
//        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
//        giftCertificateDto.setName("Hello");
//        giftCertificateDto.setDescription("Hello from description");
//        giftCertificateDto.setPrice(new BigDecimal("123"));
//        giftCertificateDto.setDuration(1);
//        Set<TagDto> tagsDto = new HashSet<>();
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Hi");
//        tagsDto.add(tagDto);
//        giftCertificateDto.setTags(tagsDto);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//        giftCertificate.setTags(tags);
//
//        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
//        when(tagDao.findTagByName(tag.getName())).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.addGiftCertificate(giftCertificateDto));
//    }
//
//    @Test
//    void whenAddTagToGiftCertificateThenShouldReturnSetOfTagsDto() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Hi");
//
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setTags(tags);
//
//        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
//        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
//        when(tagDao.findTagByName(tag.getName())).thenReturn(Optional.of(tag));
//        when(giftCertificateDao.update(giftCertificate)).thenReturn(giftCertificate);
//
//        Set<TagDto> tagsDto = giftCertificateService.addTagToGiftCertificate(giftCertificate.getId(), tagDto);
//
//        assertEquals(giftCertificate.getTags(), tagsDto.stream().map(t -> modelMapper.map(t, Tag.class)).collect(Collectors.toSet()));
//    }
//
//    @Test
//    void whenAddTagToGiftCertificateThenShouldThrowException() {
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Hi");
//
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//
//        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
//        when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
//        when(tagDao.findTagByName(tag.getName())).thenThrow(ResourceNotFoundException.class);
//
//        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.addTagToGiftCertificate(giftCertificate.getId(), tagDto));
//    }
//
//    @Test
//    void whenFindGiftCertificateByIdThenShouldReturnGiftCertificate() {
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setTags(tags);
//
//        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
//        GiftCertificateDto mockedGiftCertificateDto = giftCertificateService.findGiftCertificateById(giftCertificate.getId());
//
//        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
//    }
//
//    @Test
//    void whenFindGiftCertificateByIdThenShouldThrowException() {
//        assertThrows(ValidationException.class, () -> giftCertificateService.findGiftCertificateById(-1));
//    }
//
//    @Test
//    void whenFindGiftCertificateTagsThenShouldReturnSetOfTagsDto() {
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setTags(tags);
//
//        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
//        Set<TagDto> tagsDto = giftCertificateService.findGiftCertificateTags(giftCertificate.getId());
//
//        assertEquals(tags, tagsDto.stream().map(t -> modelMapper.map(t, Tag.class)).collect(Collectors.toSet()));
//    }
//
//    @Test
//    void whenFindGiftCertificateTagsThenShouldThrowException() {
//        assertThrows(ValidationException.class, () -> giftCertificateService.findGiftCertificateTags(-1));
//    }
//
//    @Test
//    void whenFindGiftCertificatesByParametersThenShouldReturnListOfGiftCertificate() {
//        Map<String, String> queryParameters = new HashMap<>();
//        queryParameters.put("name", "Hello");
//
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setTags(tags);
//
//        when(giftCertificateDao.findAllByParameters(anyMap())).thenReturn(Collections.singletonList(giftCertificate));
//
//        List<GiftCertificateDto> giftCertificatesDto = giftCertificateService.findGiftCertificatesByParameters(queryParameters, page, perPage);
//
//        assertEquals(Collections.singletonList(giftCertificate), giftCertificatesDto.stream()
//                .map(giftCertificateDto -> modelMapper.map(giftCertificateDto, GiftCertificate.class))
//                .collect(Collectors.toList()));
//    }
//
//    @Test
//    void whenFindGiftCertificatesByParametersThenShouldThrowException() {
//        Map<String, String> queryParameters = new HashMap<>();
//        queryParameters.put("name", "!!^%#");
//        assertThrows(ValidationException.class, () -> giftCertificateService.findGiftCertificatesByParameters(queryParameters, page, perPage));
//    }
//
//    @Test
//    void whenDeleteGiftCertificateByIdThenShouldNotThrowException() {
//        int certificateId = 1;
//        doNothing().when(giftCertificateDao).removeById(certificateId);
//        when(orderDao.checkIfCertificateUsed(certificateId)).thenReturn(false);
//        assertDoesNotThrow(() -> giftCertificateService.deleteGiftCertificateById(certificateId));
//    }
//
//    @Test
//    void whenDeleteGiftCertificateByIdThenShouldThrowException() {
//        int certificateId = 1;
//        doNothing().when(giftCertificateDao).removeById(certificateId);
//        when(orderDao.checkIfCertificateUsed(certificateId)).thenReturn(true);
//        assertThrows(DeleteResourceException.class, () -> giftCertificateService.deleteGiftCertificateById(certificateId));
//    }
//
//    @Test
//    void whenDeleteTagFromGiftCertificateThenShouldNotThrowException() {
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setTags(tags);
//
//        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.ofNullable(giftCertificate));
//        when(tagDao.findById(tag.getTagId())).thenReturn(Optional.ofNullable(tag));
//        when(giftCertificateDao.update(giftCertificate)).thenReturn(giftCertificate);
//        assertDoesNotThrow(() -> giftCertificateService.deleteTagFromGiftCertificate(giftCertificate.getId(), tag.getTagId()));
//    }
//
//    @Test
//    void whenDeleteTagFromGiftCertificateThenShouldThrowException() {
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//
//        when(giftCertificateDao.findById(giftCertificate.getId())).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.deleteTagFromGiftCertificate(giftCertificate.getId(), tag.getTagId()));
//    }
//
//    @Test
//    void whenUpdateGiftCertificateThenShouldReturnUpdatedGiftCertificate() {
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
//        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
//        giftCertificate.setTags(tags);
//
//        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
//        giftCertificateDto.setName("Hello");
//        giftCertificateDto.setDescription("Hello from description");
//        giftCertificateDto.setPrice(new BigDecimal("1233"));
//        giftCertificateDto.setDuration(1);
//        giftCertificateDto.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
//        giftCertificateDto.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
//        Set<TagDto> tagsDto = new HashSet<>();
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Hellotag");
//        tagsDto.add(tagDto);
//        giftCertificateDto.setTags(tagsDto);
//
//        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
//        when(tagDao.findAll()).thenReturn(Collections.singletonList(modelMapper.map(tagDto, Tag.class)));
//        when(tagDao.findTagByName(tagDto.getName())).thenReturn(Optional.of(modelMapper.map(tagDto, Tag.class)));
//        when(giftCertificateDao.update(giftCertificate)).thenReturn(giftCertificate);
//        GiftCertificateDto mockedGiftCertificateDto = giftCertificateService
//                .updateGiftCertificate(giftCertificate.getId(), giftCertificateDto);
//
//        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
//    }
//
//    @Test
//    void whenUpdateGiftCertificateThenShouldThrowException() {
//        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
//        giftCertificateDto.setName("Hello");
//        giftCertificateDto.setDescription("Hello from description");
//        giftCertificateDto.setPrice(new BigDecimal("123"));
//        giftCertificateDto.setDuration(1);
//
//        assertThrows(ValidationException.class, () -> giftCertificateService.updateGiftCertificate(-123, giftCertificateDto));
//    }
//
//    public static Object[][] fieldsForUpdateGiftCertificateField() {
//        GiftCertificateField price = new GiftCertificateField();
//        price.setFieldName("price");
//        price.setFieldValue("1233");
//        GiftCertificateField name = new GiftCertificateField();
//        name.setFieldName("name");
//        name.setFieldValue("Upd name");
//        GiftCertificateField description = new GiftCertificateField();
//        description.setFieldName("description");
//        description.setFieldValue("Upd desc");
//        GiftCertificateField duration = new GiftCertificateField();
//        duration.setFieldName("duration");
//        duration.setFieldValue("1");
//        return new Object[][]{
//                {price}, {name}, {description}, {duration}
//        };
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("fieldsForUpdateGiftCertificateField")
//    void whenUpdateGiftCertificateFieldThenShouldReturnUpdatedGiftCertificate(GiftCertificateField giftCertificateField) {
//
//        Set<Tag> tags = new HashSet<>();
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        tags.add(tag);
//
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
//        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
//        giftCertificate.setTags(tags);
//
//        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
//        giftCertificateDto.setName("Hello");
//        giftCertificateDto.setDescription("Hello from description");
//        giftCertificateDto.setPrice(new BigDecimal("1233"));
//        giftCertificateDto.setDuration(1);
//        giftCertificateDto.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
//        giftCertificateDto.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
//        Set<TagDto> tagsDto = new HashSet<>();
//        TagDto tagDto = new TagDto();
//        tagDto.setName("Hellotag");
//        tagsDto.add(tagDto);
//        giftCertificateDto.setTags(tagsDto);
//
//        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
//        when(giftCertificateDao.update(giftCertificate)).thenReturn(giftCertificate);
//        GiftCertificateDto mockedGiftCertificateDto = giftCertificateService
//                .updateGiftCertificateField(giftCertificate.getId(), giftCertificateField);
//
//        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
//    }
//
//
//    @Test
//    void whenUpdateGiftCertificateFieldThenShouldThrowException() {
//        GiftCertificateField price = new GiftCertificateField();
//        price.setFieldName("price");
//        price.setFieldValue("1233s");
//        assertThrows(ValidationException.class, () -> giftCertificateService.updateGiftCertificateField(123, price));
//    }
//}