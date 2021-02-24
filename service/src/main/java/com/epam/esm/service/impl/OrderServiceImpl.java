package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ResponseGiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final TagRepository tagRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDto makeOrder(long userId, List<Long> giftCertificateIds) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificateIds.forEach(id -> {
            GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.GIFT_CERTIFICATE_WITH_ID_NOT_FOUND, id));
            giftCertificates.add(giftCertificate);
        });
        BigDecimal cost = giftCertificates.stream().map(GiftCertificate::getPrice)
                .reduce(BigDecimal::add).get();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_WITH_ID_NOT_FOUND, userId));
        Order order = new Order();
        order.setUser(user);
        order.setGiftCertificates(giftCertificates);
        order.setCost(cost);
        long orderId = orderRepository.save(order).getOrderId();
        order.setOrderId(orderId);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<ResponseGiftCertificateDto> findUserOrderGiftCertificates(long userId, long orderId) {
        List<GiftCertificate> giftCertificates = orderRepository.findUserOrderGiftCertificates(userId, orderId);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, ResponseGiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto mostWidelyUsedTagWithHighestCostOfAllOrders() {
        List<User> users = userRepository.findUserWithHighestCostOfAllOrders();
        User user = users.stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_WITH_HIGHEST_COST_ORDERS_NOT_FOUND));
        long userId = user.getUserId();
        long tagId = orderRepository.findMostWidelyUsedTagByUserId(userId).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.MOST_WIDELY_USED_TAG_NOT_FOUND));
        Tag foundTag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_ID_NOT_FOUND, tagId));
        return modelMapper.map(foundTag, TagDto.class);
    }
}