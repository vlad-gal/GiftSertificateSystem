package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserDto findUserById(long id);

    List<UserDto> findAllUsersByParameters(Map<String, String> queryParameters, int page, int perPage);

    List<OrderDto> findUserOrders(long userId);

    OrderDto findUserOrder(long orderId, long userId);
}