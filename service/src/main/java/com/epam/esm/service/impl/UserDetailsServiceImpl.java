package com.epam.esm.service.impl;

import com.epam.esm.dto.SecurityUser;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String ADMIN = "ADMIN";
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        return new SecurityUser(user.getUserId(), user.getLogin(), user.getPassword(),
                user.getRole().getPermissions().stream().map(permission ->
                        new SimpleGrantedAuthority(permission.getPermissionName()))
                        .collect(Collectors.toList()),
                user.getRole().getRoleName().equals(ADMIN));
    }
}
