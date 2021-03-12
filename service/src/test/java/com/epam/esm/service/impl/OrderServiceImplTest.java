package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ResponseGiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {
    private GiftCertificateRepository giftCertificateRepository = mock(GiftCertificateRepository.class);
    private TagRepository tagRepository = mock(TagRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    private OrderService orderService =
            new OrderServiceImpl(tagRepository, orderRepository, userRepository, giftCertificateRepository, modelMapper);

    @Test
    void whenMakeOrderThenShouldReturnOrder() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);

        User user = new User();
        user.setUserId(1);
        user.setFirstName("Петя");
        user.setLastName("Петечкин");
        user.setLogin("pyatro");

        Order order = new Order();
        order.setOrderId(1);
        order.setPurchaseDate(LocalDateTime.now());
        order.setGiftCertificates(Collections.singletonList(giftCertificate));
        order.setCost(giftCertificate.getPrice());

        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderDto orderDto = orderService.makeOrder(user.getUserId(), Collections.singletonList(giftCertificate.getId()));
        orderDto.setPurchaseDate(order.getPurchaseDate());
        assertEquals(order.getCost(), modelMapper.map(orderDto, Order.class).getCost());
    }

    @Test
    void whenMakeOrderThenShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(145);
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> orderService.makeOrder(1, Collections.singletonList(145L)));
    }

    @Test
    void whenFindUserOrderGiftCertificatesThenShouldReturnListOfGiftCertificates() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123"));
        giftCertificate.setDuration(1);

        Order order = new Order();
        order.setOrderId(1);
        when(orderRepository.findUserOrderGiftCertificates(order.getOrderId(), 1)).thenReturn(Collections.singletonList(giftCertificate));
        List<ResponseGiftCertificateDto> foundGiftCertificates = orderService.findUserOrderGiftCertificates(order.getOrderId(), 1);
        assertEquals(Collections.singletonList(giftCertificate),
                foundGiftCertificates.stream()
                        .map(giftCertificateDto -> modelMapper.map(giftCertificateDto, GiftCertificate.class)).collect(Collectors.toList()));
    }

    @Test
    void whenFindMostWidelyUsedTagWithHighestCostOfAllOrdersThenShouldReturnTag() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Петя");
        user.setLastName("Петечкин");
        user.setLogin("pyatro");

        Tag tag = new Tag();
        tag.setTagId(1);
        tag.setName("Hi");
        when(userRepository.findUserWithHighestCostOfAllOrders()).thenReturn(Collections.singletonList(user));
        when(orderRepository.findMostWidelyUsedTagByUserId(user.getUserId())).thenReturn(Collections.singletonList(tag.getTagId()));
        when(tagRepository.findById(tag.getTagId())).thenReturn(Optional.of(tag));

        TagDto tagDto = orderService.mostWidelyUsedTagWithHighestCostOfAllOrders();

        assertEquals(tag, modelMapper.map(tagDto, Tag.class));
    }

    @Test
    void whenFindMostWidelyUsedTagWithHighestCostOfAllOrdersThenShouldThrowException() {
        when(userRepository.findUserWithHighestCostOfAllOrders()).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> orderService.mostWidelyUsedTagWithHighestCostOfAllOrders());
    }
}