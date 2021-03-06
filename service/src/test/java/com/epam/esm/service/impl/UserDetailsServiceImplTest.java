package com.epam.esm.service.impl;

import com.epam.esm.entity.Permission;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.util.converter.UserCredentialInformationConverter;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {
    private UserRepository userRepository = mock(UserRepository.class);
    private ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        modelMapper.addConverter(new UserCredentialInformationConverter());
    }

    private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userRepository, modelMapper);

    @Test
    void whenLoadUserByUsernameThenShouldReturnSecurityUser() {
        Role role = new Role();
        role.setRoleName("user");
        Permission permission = new Permission();
        permission.setPermissionName("123");
        role.setPermissions(Collections.singletonList(permission));
        User user = new User();
        user.setUserId(1);
        user.setLogin("login");
        user.setRole(role);
        when(userRepository.findByLogin("login")).thenReturn(Optional.of(user));
        UserDetails userDetails = userDetailsService.loadUserByUsername("login");
        assertEquals(user.getLogin(), userDetails.getUsername());
    }

    @Test
    void whenLoadUserByUsernameThenShouldThrowException() {
        when(userRepository.findByLogin("login")).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> userDetailsService.loadUserByUsername("login"));
    }
}