package com.epam.esm.util.converter;

import com.epam.esm.dto.UserCredentialInformation;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.modelmapper.AbstractConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserCredentialInformationConverter extends AbstractConverter<User, UserCredentialInformation> {
    private static final String ADMIN = "ADMIN";

    @Override
    protected UserCredentialInformation convert(User source) {
        return new UserCredentialInformation(source.getUserId(), source.getLogin(),
                source.getPassword(), convertToGrantedAuthorities(source.getRole()),
                source.getRole().getRoleName().equals(ADMIN));
    }

    private List<SimpleGrantedAuthority> convertToGrantedAuthorities(Role role) {
        return role.getPermissions().stream().map(permission ->
                new SimpleGrantedAuthority(permission.getPermissionName()))
                .collect(Collectors.toList());
    }
}
