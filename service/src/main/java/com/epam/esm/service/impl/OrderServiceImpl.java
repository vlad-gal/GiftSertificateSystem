package com.epam.esm.service.impl;

//import com.epam.esm.dto.GiftCertificateDto;

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
//import com.epam.esm.validator.GiftCertificateValidator;
//import com.epam.esm.validator.OrderValidator;
//import com.epam.esm.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
//        UserValidator.isValidId(userId);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificateIds.forEach(id -> {
//            GiftCertificateValidator.isValidId(id);
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
    public OrderDto findOrderById(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.ORDER_WITH_ID_NOT_FOUND, orderId));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<ResponseGiftCertificateDto> findOrderGiftCertificates(long orderId) {
        List<GiftCertificate> giftCertificates = orderRepository.findOrderGiftCertificates(orderId);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, ResponseGiftCertificateDto.class)).collect(Collectors.toList());
    }

    @Override
    public TagDto mostWidelyUsedTagWithHighestCostOfAllOrders() {
        User user = userRepository.findUserWithHighestCostOfAllOrders().get(0);
        long userId = user.getUserId();
        long tagId = orderRepository.findMostWidelyUsedTagByUserId(userId).get(0);
        Tag foundTag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_ID_NOT_FOUND, tagId));
        return modelMapper.map(foundTag, TagDto.class);
    }
}