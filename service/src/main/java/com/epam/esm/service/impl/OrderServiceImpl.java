package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.OrderValidator;
import com.epam.esm.validator.UserValidator;
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
@Service
public class OrderServiceImpl implements OrderService {
    private final TagDao tagDao;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(TagDao tagDao, OrderDao orderDao, UserDao userDao,
                            GiftCertificateDao giftCertificateDao, ModelMapper modelMapper) {
        this.tagDao = tagDao;
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    public OrderDto makeOrder(long userId, List<Long> giftCertificateIds) {
        UserValidator.isValidId(userId);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificateIds.forEach(id -> {
            GiftCertificateValidator.isValidId(id);
            GiftCertificate giftCertificate = giftCertificateDao.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.GIFT_CERTIFICATE_WITH_ID_NOT_FOUND, id));
            giftCertificates.add(giftCertificate);
        });
        BigDecimal cost = giftCertificates.stream().map(giftCertificate -> giftCertificate.getPrice())
                .reduce(BigDecimal::add).get();
        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_WITH_ID_NOT_FOUND, userId));
        ;
        Order order = new Order();
        order.setUser(user);
        order.setGiftCertificates(giftCertificates);
        order.setCost(cost);
        long orderId = orderDao.add(order);
        order.setOrderId(orderId);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto findOrderById(long orderId) {
        OrderValidator.isValidId(orderId);
        Optional<Order> optionalOrder = orderDao.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.ORDER_WITH_ID_NOT_FOUND, orderId));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findOrderGiftCertificates(long orderId) {
        OrderValidator.isValidId(orderId);
        List<GiftCertificate> giftCertificates = orderDao.findOrderGiftCertificates(orderId);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagDto mostWidelyUsedTagWithHighestCostOfAllOrders() {
        User user = userDao.findUserWithHighestCostOfAllOrders()
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_WITH_HIGHEST_COST_ORDERS_NOT_FOUND));
        long userId = user.getUserId();
        long tagId = orderDao.findMostWidelyUsedTag(userId);
        Tag foundTag = tagDao.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.TAG_WITH_ID_NOT_FOUND, tagId));
        return modelMapper.map(foundTag, TagDto.class);
    }
}