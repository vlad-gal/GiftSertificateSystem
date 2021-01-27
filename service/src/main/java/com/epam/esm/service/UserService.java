package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();

    UserDto findUserById(long id);
}
