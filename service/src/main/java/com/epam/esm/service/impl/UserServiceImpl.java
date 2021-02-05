package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ParameterManager;
import com.epam.esm.validator.OrderValidator;
import com.epam.esm.validator.QueryParameterValidator;
import com.epam.esm.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final OrderDao orderDao;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserDao userDao, OrderDao orderDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.orderDao = orderDao;
    }

    @Override
    @Transactional
    public UserDto findUserById(long id) {
        UserValidator.isValidId(id);
        User user = checkAndGetUser(id);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public List<UserDto> findAllUsersByParameters(Map<String, String> queryParameters) {
        Map<String, String> processedQueryParameters = ParameterManager.defaultQueryParametersProcessing(queryParameters);
        QueryParameterValidator.isValidUserQueryParameters(processedQueryParameters);
        log.info("Query parameter: {}", processedQueryParameters);
        List<User> users = userDao.findAllByParameters(processedQueryParameters);
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<OrderDto> findUserOrders(long userId) {
        UserValidator.isValidId(userId);
        List<Order> orders = orderDao.findUserOrders(userId);
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto findUserOrder(long userId, long orderId) {
        UserValidator.isValidId(userId);
        OrderValidator.isValidId(orderId);
        Optional<Order> orderOptional = orderDao.findUserOrder(userId, orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_ORDER_NOT_FOUND, userId, orderId));
        return modelMapper.map(order, OrderDto.class);
    }

    private User checkAndGetUser(long id) {
        Optional<User> userOptional = userDao.findById(id);
        return userOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_WITH_ID_NOT_FOUND, id));
    }
}