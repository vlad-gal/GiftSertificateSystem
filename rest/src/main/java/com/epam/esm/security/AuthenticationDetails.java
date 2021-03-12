package com.epam.esm.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDetails {
    private long id;
    private boolean isAdmin;
}