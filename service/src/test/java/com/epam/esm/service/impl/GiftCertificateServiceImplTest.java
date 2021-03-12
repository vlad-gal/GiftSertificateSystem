package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.dto.RequestGiftCertificateDto;
import com.epam.esm.dto.ResponseGiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ParameterManager;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class GiftCertificateServiceImplTest {
    private GiftCertificateRepository giftCertificateRepository = mock(GiftCertificateRepository.class);
    private TagRepository tagRepository = mock(TagRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    private GiftCertificateService giftCertificateService =
            new GiftCertificateServiceImpl(giftCertificateRepository, tagRepository, orderRepository, modelMapper);

    @Test
    void whenAddGiftCertificateThenShouldReturnResponseGiftCertificateDto() {
        RequestGiftCertificateDto giftCertificateDto = new RequestGiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("123"));
        giftCertificateDto.setDuration(1);
        Set<TagDto> tagsDto = new HashSet<>();
        TagDto tagDto = new TagDto();
        tagDto.setName("Hi");
        tagsDto.add(tagDto);
        TagDto newTagDto = new TagDto();
        newTagDto.setName("New TAG");
        tagsDto.add(newTagDto);
        giftCertificateDto.setTags(tagsDto);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        tags.add(tag);
        Tag newTag = new Tag();
        newTag.setName("New TAG");
        tags.add(newTag);

        when(tagRepository.findAll()).thenReturn(Collections.singletonList(tag));
        when(tagRepository.findTagByName(tag.getName())).thenReturn(Optional.of(tag));
        when(giftCertificateRepository.save(any(GiftCertificate.class))).thenReturn(giftCertificate);

        ResponseGiftCertificateDto mockedGiftCertificateDto = giftCertificateService.addGiftCertificate(giftCertificateDto);
        giftCertificate.setCreatedDate(mockedGiftCertificateDto.getCreatedDate());
        giftCertificate.setLastUpdateDate(mockedGiftCertificateDto.getLastUpdateDate());
        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
    }

    @Test
    void whenAddGiftCertificateThenShouldThrowException() {
        RequestGiftCertificateDto giftCertificateDto = new RequestGiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("123"));
        giftCertificateDto.setDuration(1);
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
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        tags.add(tag);

        when(tagRepository.findAll()).thenReturn(Collections.singletonList(tag));
        when(tagRepository.findTagByName(tag.getName())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.addGiftCertificate(giftCertificateDto));
    }

    @Test
    void whenAddTagToGiftCertificateThenShouldReturnSetOfTagsDto() {
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
        giftCertificate.setTags(tags);

        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        when(tagRepository.findAll()).thenReturn(Collections.singletonList(tag));
        when(tagRepository.findTagByName(tag.getName())).thenReturn(Optional.of(tag));
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);

        Set<TagDto> tagsDto = giftCertificateService.addTagToGiftCertificate(giftCertificate.getId(), tagDto);

        assertEquals(giftCertificate.getTags(), tagsDto.stream().map(t -> modelMapper.map(t, Tag.class)).collect(Collectors.toSet()));
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

        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        when(tagRepository.findAll()).thenReturn(Collections.singletonList(tag));
        when(tagRepository.findTagByName(tag.getName())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.addTagToGiftCertificate(giftCertificate.getId(), tagDto));
    }

    @Test
    void whenFindGiftCertificateByIdThenShouldReturnGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);

        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        ResponseGiftCertificateDto mockedGiftCertificateDto = giftCertificateService.findGiftCertificateById(giftCertificate.getId());

        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
    }

    @Test
    void whenFindGiftCertificateByIdThenShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateById(-1));
    }

    @Test
    void whenFindGiftCertificateTagsThenShouldReturnSetOfTagsDto() {
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
        giftCertificate.setTags(tags);

        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        Set<TagDto> tagsDto = giftCertificateService.findGiftCertificateTags(giftCertificate.getId());

        assertEquals(tags, tagsDto.stream().map(t -> modelMapper.map(t, Tag.class)).collect(Collectors.toSet()));
    }

    @Test
    void whenFindGiftCertificateTagsThenShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateTags(-1));
    }

    @Test
    void whenFindGiftCertificatesByParametersThenShouldReturnListOfGiftCertificates() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("name", "Hello");
        Predicate predicate = ParameterManager.createQPredicateForGiftCertificate(queryParameters);
        Pageable pageable = PageRequest.of(0, 2);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);

        Page<GiftCertificate> giftCertificates = new PageImpl<>(Collections.singletonList(giftCertificate));

        when(giftCertificateRepository.findAll(predicate, pageable)).thenReturn(giftCertificates);

        List<ResponseGiftCertificateDto> giftCertificatesDto = giftCertificateService.findGiftCertificatesByParameters(queryParameters, 0, 2);

        assertEquals(Collections.singletonList(giftCertificate), giftCertificatesDto.stream()
                .map(giftCertificateDto -> modelMapper.map(giftCertificateDto, GiftCertificate.class))
                .collect(Collectors.toList()));
    }

    @Test
    void whenFindGiftCertificatesWithoutParametersThenShouldReturnListOfGiftCertificates() {
        Map<String, String> queryParameters = new HashMap<>();

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);

        Page<GiftCertificate> giftCertificates = new PageImpl<>(Collections.singletonList(giftCertificate));

        when(giftCertificateRepository.findAll(any(Pageable.class))).thenReturn(giftCertificates);

        List<ResponseGiftCertificateDto> giftCertificatesDto = giftCertificateService.findGiftCertificatesByParameters(queryParameters, 0, 2);

        assertEquals(Collections.singletonList(giftCertificate), giftCertificatesDto.stream()
                .map(giftCertificateDto -> modelMapper.map(giftCertificateDto, GiftCertificate.class))
                .collect(Collectors.toList()));
    }

    @Test
    void whenFindGiftCertificatesByParametersThenShouldThrowException() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("name", "!!^%#");
        assertThrows(ValidationException.class, () -> giftCertificateService.findGiftCertificatesByParameters(queryParameters, 0, 2));
    }

    @Test
    void whenDeleteGiftCertificateByIdThenShouldNotThrowException() {
        long certificateId = 1;
        when(orderRepository.findOrdersWhereGiftCertificateUsed(certificateId)).thenReturn(Collections.emptyList());
        doNothing().when(giftCertificateRepository).deleteById(certificateId);
        assertDoesNotThrow(() -> giftCertificateService.deleteGiftCertificateById(certificateId));
    }

    @Test
    void whenDeleteGiftCertificateByIdThenShouldThrowException() {
        long certificateId = 1;
        Order order = new Order();
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderRepository.findOrdersWhereGiftCertificateUsed(certificateId)).thenReturn(orders);
        doNothing().when(giftCertificateRepository).deleteById(certificateId);
        assertThrows(DeleteResourceException.class, () -> giftCertificateService.deleteGiftCertificateById(certificateId));
    }

    @Test
    void whenDeleteTagFromGiftCertificateThenShouldNotThrowException() {
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
        giftCertificate.setTags(tags);

        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.ofNullable(giftCertificate));
        when(tagRepository.findById(tag.getTagId())).thenReturn(Optional.ofNullable(tag));
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        assertDoesNotThrow(() -> giftCertificateService.deleteTagFromGiftCertificate(giftCertificate.getId(), tag.getTagId()));
    }

    @Test
    void whenDeleteTagFromGiftCertificateThenShouldThrowException() {
        Tag tag = new Tag();
        tag.setTagId(1);
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);

        when(giftCertificateRepository.findById(giftCertificate.getId())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.deleteTagFromGiftCertificate(giftCertificate.getId(), tag.getTagId()));
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

        RequestGiftCertificateDto giftCertificateDto = new RequestGiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("1233"));
        giftCertificateDto.setDuration(1);
        Set<TagDto> tagsDto = new HashSet<>();
        TagDto tagDto = new TagDto();
        tagDto.setName("Hellotag");
        tagsDto.add(tagDto);
        giftCertificateDto.setTags(tagsDto);

        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        when(tagRepository.findAll()).thenReturn(Collections.singletonList(modelMapper.map(tagDto, Tag.class)));
        when(tagRepository.findTagByName(tagDto.getName())).thenReturn(Optional.of(modelMapper.map(tagDto, Tag.class)));
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        ResponseGiftCertificateDto mockedGiftCertificateDto = giftCertificateService
                .updateGiftCertificate(giftCertificate.getId(), giftCertificateDto);

        assertEquals(giftCertificate.getPrice(), modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class).getPrice());
    }

    @Test
    void whenUpdateGiftCertificateThenShouldThrowException() {
        RequestGiftCertificateDto giftCertificateDto = new RequestGiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("123"));
        giftCertificateDto.setDuration(1);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.updateGiftCertificate(-123, giftCertificateDto));
    }

    public static Object[][] fieldsForUpdateGiftCertificateField() {
        GiftCertificateField price = new GiftCertificateField();
        price.setFieldName("price");
        price.setFieldValue("1233");
        GiftCertificateField name = new GiftCertificateField();
        name.setFieldName("name");
        name.setFieldValue("Upd name");
        GiftCertificateField description = new GiftCertificateField();
        description.setFieldName("description");
        description.setFieldValue("Upd desc");
        GiftCertificateField duration = new GiftCertificateField();
        duration.setFieldName("duration");
        duration.setFieldValue("1");
        return new Object[][]{
                {price}, {name}, {description}, {duration}
        };
    }

    @ParameterizedTest
    @MethodSource("fieldsForUpdateGiftCertificateField")
    void whenUpdateGiftCertificateFieldThenShouldReturnUpdatedGiftCertificate(GiftCertificateField giftCertificateField) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreatedDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2012, 12, 2, 14, 56, 44));

        RequestGiftCertificateDto giftCertificateDto = new RequestGiftCertificateDto();
        giftCertificateDto.setName("Hello");
        giftCertificateDto.setDescription("Hello from description");
        giftCertificateDto.setPrice(new BigDecimal("1233"));
        giftCertificateDto.setDuration(1);

        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        ResponseGiftCertificateDto mockedGiftCertificateDto = giftCertificateService
                .updateGiftCertificateField(giftCertificate.getId(), giftCertificateField);

        assertEquals(giftCertificate, modelMapper.map(mockedGiftCertificateDto, GiftCertificate.class));
    }


    @Test
    void whenUpdateGiftCertificateFieldThenShouldThrowException() {
        GiftCertificateField price = new GiftCertificateField();
        price.setFieldName("price");
        price.setFieldValue("1233s");
        assertThrows(ValidationException.class, () -> giftCertificateService.updateGiftCertificateField(123, price));
    }
}