//package com.epam.esm.service.impl;
//
//import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
//import com.epam.esm.dao.impl.OrderDaoImpl;
//import com.epam.esm.dao.impl.TagDaoImpl;
//import com.epam.esm.dto.GiftCertificateDto;
//import com.epam.esm.dto.OrderDto;
//import com.epam.esm.dto.TagDto;
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Order;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.entity.User;
//import com.epam.esm.exception.ResourceNotFoundException;
//import com.epam.esm.exception.ValidationException;
//import com.epam.esm.service.OrderService;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.config.Configuration;
//import org.modelmapper.convention.MatchingStrategies;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class OrderServiceImplTest {
//    private GiftCertificateDao giftCertificateDao = mock(GiftCertificateDaoImpl.class);
//    private TagDao tagDao = mock(TagDaoImpl.class);
//    private OrderDao orderDao = mock(OrderDaoImpl.class);
//    private UserDao userDao = mock(UserDao.class);
//    private ModelMapper modelMapper = new ModelMapper();
//
//    {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
//                .setFieldMatchingEnabled(true)
//                .setSkipNullEnabled(true)
//                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
//    }
//
//    private OrderService orderService =
//            new OrderServiceImpl(tagDao, orderDao, userDao, giftCertificateDao, modelMapper);
//
//    @Test
//    void whenMakeOrderThenShouldReturnOrder() {
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//
//        User user = new User();
//        user.setUserId(1);
//        user.setFirstName("Петя");
//        user.setLastName("Петечкин");
//        user.setLogin("pyatro");
//
//        Order order = new Order();
//        order.setOrderId(1);
//        order.setPurchaseDate(LocalDateTime.now());
//        order.setGiftCertificates(Collections.singletonList(giftCertificate));
//        order.setCost(giftCertificate.getPrice());
//
//        when(giftCertificateDao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
//        when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
//        when(orderDao.add(order)).thenReturn(anyLong());
//        OrderDto orderDto = orderService.makeOrder(user.getUserId(), Collections.singletonList(giftCertificate.getId()));
//        orderDto.setPurchaseDate(order.getPurchaseDate());
//        orderDto.setOrderId(1);
//        assertEquals(order, modelMapper.map(orderDto, Order.class));
//    }
//
//    @Test
//    void whenMakeOrderThenShouldThrowException() {
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(145);
//        when(giftCertificateDao.findById(giftCertificate.getId())).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> orderService.makeOrder(1, Collections.singletonList(145L)));
//    }
//
//    @Test
//    void whenFindOrderByExistIdThenShouldReturnOrder() {
//        Order order = new Order();
//        order.setOrderId(1);
//        when(orderDao.findById(order.getOrderId())).thenReturn(Optional.of(order));
//        OrderDto foundOrder = orderService.findOrderById(order.getOrderId());
//        assertEquals(order, modelMapper.map(foundOrder, Order.class));
//    }
//
//    @Test
//    void whenFindOrderByNotExistIdThenShouldThrowException() {
//        Order order = new Order();
//        order.setOrderId(3312331);
//        when(orderDao.findById(order.getOrderId())).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> orderService.findOrderById(order.getOrderId()));
//    }
//
//    @Test
//    void whenFindOrderGiftCertificatesThenShouldReturnListOfGiftCertificates() {
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1);
//        giftCertificate.setName("Hello");
//        giftCertificate.setDescription("Hello from description");
//        giftCertificate.setPrice(new BigDecimal("123"));
//        giftCertificate.setDuration(1);
//
//        Order order = new Order();
//        order.setOrderId(1);
//        when(orderDao.findOrderGiftCertificates(order.getOrderId())).thenReturn(Collections.singletonList(giftCertificate));
//        List<GiftCertificateDto> foundGiftCertificates = orderService.findOrderGiftCertificates(order.getOrderId());
//        assertEquals(Collections.singletonList(giftCertificate),
//                foundGiftCertificates.stream()
//                        .map(giftCertificateDto -> modelMapper.map(giftCertificateDto, GiftCertificate.class)).collect(Collectors.toList()));
//    }
//
//    @Test
//    void whenFindOrderGiftCertificatesThenShouldThrowException() {
//        Order order = new Order();
//        order.setOrderId(-124423);
//        assertThrows(ValidationException.class, () -> orderService.findOrderGiftCertificates(order.getOrderId()));
//    }
//
//    @Test
//    void whenFindMostWidelyUsedTagWithHighestCostOfAllOrdersThenShouldReturnTag() {
//        User user = new User();
//        user.setUserId(1);
//        user.setFirstName("Петя");
//        user.setLastName("Петечкин");
//        user.setLogin("pyatro");
//
//        Tag tag = new Tag();
//        tag.setTagId(1);
//        tag.setName("Hi");
//        when(userDao.findUserWithHighestCostOfAllOrders()).thenReturn(Optional.of(user));
//        when(orderDao.findMostWidelyUsedTag(user.getUserId())).thenReturn(tag.getTagId());
//        when(tagDao.findById(tag.getTagId())).thenReturn(Optional.of(tag));
//
//        TagDto tagDto = orderService.mostWidelyUsedTagWithHighestCostOfAllOrders();
//
//        assertEquals(tag, modelMapper.map(tagDto, Tag.class));
//    }
//
//    @Test
//    void whenFindMostWidelyUsedTagWithHighestCostOfAllOrdersThenShouldThrowException() {
//        when(userDao.findUserWithHighestCostOfAllOrders()).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> orderService.mostWidelyUsedTagWithHighestCostOfAllOrders());
//    }
//}