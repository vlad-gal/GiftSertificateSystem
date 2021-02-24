package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionPropertyKey;
import com.epam.esm.exception.InvalidCredentialsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UserAlreadyExistException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ParameterManager;
import com.epam.esm.validator.QueryParameterValidator;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto add(RegistrationUserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getRepeatedPassword())) {
            throw new InvalidCredentialsException(ExceptionPropertyKey.INCORRECT_CREDENTIALS);
        }
        if (userRepository.existsByLogin(userDto.getLogin())) {
            throw new UserAlreadyExistException(ExceptionPropertyKey.USER_ALREADY_EXIST);
        }
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleRepository.findDefaultRole());
        User addedUser = userRepository.save(user);
        return modelMapper.map(addedUser, UserDto.class);
    }

    @Override
    public UserDto findUserById(long id) {
        User user = checkAndGetUser(id);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsersByParameters(Map<String, String> queryParameters, int page, int perPage) {
        QueryParameterValidator.isValidUserQueryParameters(queryParameters);
        Predicate predicate = ParameterManager.createQPredicateForUser(queryParameters);
        Sort sort = ParameterManager.createSort(queryParameters);
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<User> users;
        if (predicate == null) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findAll(predicate, pageable);
        }
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findUserOrders(long userId) {
        List<Order> orders = orderRepository.findOrdersByUserUserId(userId);
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    @Override
    public OrderDto findUserOrder(long orderId, long userId) {
        Optional<Order> orderOptional = orderRepository.findOrderByOrderIdAndUserUserId(orderId, userId);
        Order order = orderOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_ORDER_NOT_FOUND, userId, orderId));
        return modelMapper.map(order, OrderDto.class);
    }

//    @Override
//    public UserDto findUserByLogin(String login) {
//        User user = userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_WITH_LOGIN_NOT_FOUND, login));
//        return modelMapper.map(user, UserDto.class);
//    }

    private User checkAndGetUser(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionPropertyKey.USER_WITH_ID_NOT_FOUND, id));
    }
}