package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UserAlreadyExistException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ParameterManager;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    private UserService userService = new UserServiceImpl(userRepository, orderRepository,
            roleRepository, modelMapper, passwordEncoder);

    @Test
    void whenAddUserThenShouldReturnUser() {
        RegistrationUserDto userDto = new RegistrationUserDto();
        userDto.setFirstName("Петя");
        userDto.setLastName("Петечкин");
        userDto.setLogin("pyatro");
        userDto.setPassword("pass");
        userDto.setRepeatedPassword("pass");
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Петя");
        user.setLastName("Петечкин");
        user.setLogin("pyatro");
        user.setPassword("pass");
        Role role = new Role();
        role.setRoleName("user");
        when(userRepository.existsByLogin(userDto.getLogin())).thenReturn(false);
        when(roleRepository.findDefaultRole()).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto addedUser = userService.add(userDto);
        assertEquals(user.getLogin(), modelMapper.map(addedUser, User.class).getLogin());
    }

    @Test
    void whenAddExistUserThenShouldThrowException() {
        RegistrationUserDto userDto = new RegistrationUserDto();
        userDto.setLogin("pyatro");
        userDto.setPassword("pass");
        userDto.setRepeatedPassword("pass");
        when(userRepository.existsByLogin(userDto.getLogin())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class, () -> userService.add(userDto));
    }

    @Test
    void whenAddUserWithDifferentPasswordsThenShouldThrowException() {
        RegistrationUserDto userDto = new RegistrationUserDto();
        userDto.setLogin("pyatro");
        userDto.setPassword("123");
        userDto.setRepeatedPassword("pass");
        assertThrows(BadCredentialsException.class, () -> userService.add(userDto));
    }

    @Test
    void whenFindUserByExistIdThenShouldReturnUser() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Петя");
        user.setLastName("Петечкин");
        user.setLogin("pyatro");
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        UserDto foundUser = userService.findUserById(user.getUserId());
        assertEquals(user, modelMapper.map(foundUser, User.class));
    }

    @Test
    void whenFindUserByNotExistIdThenShouldThrowException() {
        when(userRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(1233));
    }

    @Test
    void whenFindAllUsersByParametersThenShouldReturnListOfUsers() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("firstName", "П");
        queryParameters.put("lastName", "П");
        queryParameters.put("login", "y");
        Predicate predicate = ParameterManager.createQPredicateForUser(queryParameters);
        Pageable pageable = PageRequest.of(0, 2);
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Петя");
        user.setLastName("Петечкин");
        user.setLogin("pyatro");
        Page<User> users = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findAll(predicate, pageable)).thenReturn(users);

        List<UserDto> foundUsers = userService.findAllUsersByParameters(queryParameters, 0, 2);

        assertEquals(Collections.singletonList(user),
                foundUsers.stream().map(userDto -> modelMapper.map(userDto, User.class)).collect(Collectors.toList()));
    }

    @Test
    void whenFindAllUsersWithoutParametersThenShouldReturnListOfUsers() {
        Map<String, String> queryParameters = new HashMap<>();
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Петя");
        user.setLastName("Петечкин");
        user.setLogin("pyatro");
        Page<User> users = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(users);

        List<UserDto> foundUsers = userService.findAllUsersByParameters(queryParameters, 0, 2);

        assertEquals(Collections.singletonList(user),
                foundUsers.stream().map(userDto -> modelMapper.map(userDto, User.class)).collect(Collectors.toList()));
    }

    @Test
    void whenFindAllUsersByParametersThenShouldThrowException() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("firstName", "2");
        assertThrows(ValidationException.class, () -> userService.findAllUsersByParameters(queryParameters, 0, 2));
    }

    @Test
    void whenFindUserOrdersThenShouldReturnListOfOrders() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Петя");
        user.setLastName("Петечкин");
        user.setLogin("pyatro");

        Order order = new Order();
        order.setOrderId(1);

        when(orderRepository.findOrdersByUserUserId(user.getUserId())).thenReturn(Collections.singletonList(order));

        List<OrderDto> foundOrders = userService.findUserOrders(user.getUserId());
        assertEquals(Collections.singletonList(order),
                foundOrders.stream().map(orderDto -> modelMapper.map(orderDto, Order.class)).collect(Collectors.toList()));
    }

    @Test
    void whenFindUserOrderThenShouldReturnOrder() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("Петя");
        user.setLastName("Петечкин");
        user.setLogin("pyatro");

        Order order = new Order();
        order.setOrderId(1);

        when(orderRepository.findOrderByOrderIdAndUserUserId(order.getOrderId(), user.getUserId())).thenReturn(Optional.of(order));

        OrderDto orderDto = userService.findUserOrder(order.getOrderId(), user.getUserId());
        assertEquals(order, modelMapper.map(orderDto, Order.class));
    }

    @Test
    void whenFindUserOrderThenShouldThrowException() {
        User user = new User();
        user.setUserId(1);

        Order order = new Order();
        order.setOrderId(21313123);
        when(orderRepository.findOrderByOrderIdAndUserUserId(order.getOrderId(), user.getUserId())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userService.findUserOrder(user.getUserId(), order.getOrderId()));
    }
}