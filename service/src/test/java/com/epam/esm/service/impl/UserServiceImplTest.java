//package com.epam.esm.service.impl;
//
//import com.epam.esm.dao.impl.OrderDaoImpl;
//import com.epam.esm.dto.OrderDto;
//import com.epam.esm.dto.UserDto;
//import com.epam.esm.entity.Order;
//import com.epam.esm.entity.User;
//import com.epam.esm.exception.ResourceNotFoundException;
//import com.epam.esm.exception.ValidationException;
//import com.epam.esm.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.config.Configuration;
//import org.modelmapper.convention.MatchingStrategies;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class UserServiceImplTest {
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
//    private UserService userService = new UserServiceImpl(userDao, orderDao, modelMapper);
//
//    @Test
//    void whenFindUserByExistIdThenShouldReturnUser() {
//        User user = new User();
//        user.setUserId(1);
//        user.setFirstName("Петя");
//        user.setLastName("Петечкин");
//        user.setLogin("pyatro");
//        when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
//        UserDto foundUser = userService.findUserById(user.getUserId());
//        assertEquals(user, modelMapper.map(foundUser, User.class));
//    }
//
//    @Test
//    void whenFindUserByNotExistIdThenShouldThrowException() {
//        when(userDao.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
//        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(1233));
//    }
//
//    @Test
//    void whenFindAllUsersByParametersThenShouldReturnListOfUsers() {
//        Map<String, String> queryParameters = new HashMap<>();
//        queryParameters.put("first_name", "П");
//        queryParameters.put("last_name", "П");
//        queryParameters.put("login", "y");
//
//        User user = new User();
//        user.setUserId(1);
//        user.setFirstName("Петя");
//        user.setLastName("Петечкин");
//        user.setLogin("pyatro");
//
//        when(userDao.findAllByParameters(queryParameters)).thenReturn(Collections.singletonList(user));
//
//        List<UserDto> foundUsers = userService.findAllUsersByParameters(queryParameters, page, perPage);
//
//        assertEquals(Collections.singletonList(user),
//                foundUsers.stream().map(userDto -> modelMapper.map(userDto, User.class)).collect(Collectors.toList()));
//    }
//
//    @Test
//    void whenFindAllUsersByParametersThenShouldThrowException() {
//        Map<String, String> queryParameters = new HashMap<>();
//        queryParameters.put("first_name", "2");
//        assertThrows(ValidationException.class, () -> userService.findAllUsersByParameters(queryParameters, page, perPage));
//    }
//
//    @Test
//    void whenFindUserOrdersThenShouldReturnListOfOrders() {
//        User user = new User();
//        user.setUserId(1);
//        user.setFirstName("Петя");
//        user.setLastName("Петечкин");
//        user.setLogin("pyatro");
//
//        Order order = new Order();
//        order.setOrderId(1);
//
//        when(orderDao.findUserOrders(user.getUserId())).thenReturn(Collections.singletonList(order));
//
//        List<OrderDto> foundOrders = userService.findUserOrders(user.getUserId());
//        assertEquals(Collections.singletonList(order),
//                foundOrders.stream().map(orderDto -> modelMapper.map(orderDto, Order.class)).collect(Collectors.toList()));
//    }
//
//    @Test
//    void whenFindUserOrdersThenShouldThrowException() {
//        User user = new User();
//        user.setUserId(-1231);
//        assertThrows(ValidationException.class, () -> userService.findUserOrders(user.getUserId()));
//    }
//
//    @Test
//    void whenFindUserOrderThenShouldReturnOrder() {
//        User user = new User();
//        user.setUserId(1);
//        user.setFirstName("Петя");
//        user.setLastName("Петечкин");
//        user.setLogin("pyatro");
//
//        Order order = new Order();
//        order.setOrderId(1);
//
//        when(orderDao.findUserOrder(user.getUserId(), order.getOrderId())).thenReturn(Optional.of(order));
//
//        OrderDto orderDto = userService.findUserOrder(user.getUserId(), order.getOrderId());
//        assertEquals(order, modelMapper.map(orderDto, Order.class));
//    }
//
//    @Test
//    void whenFindUserOrderThenShouldThrowException() {
//        User user = new User();
//        user.setUserId(1);
//
//        Order order = new Order();
//        order.setOrderId(-4651);
//        assertThrows(ValidationException.class, () -> userService.findUserOrder(user.getUserId(), order.getOrderId()));
//    }
//}