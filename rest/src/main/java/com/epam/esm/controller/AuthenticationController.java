package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticateRequestDto;
import com.epam.esm.dto.AuthenticateResponseDto;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<AuthenticateResponseDto> authenticate(@RequestBody @Valid AuthenticateRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        String token = jwtTokenProvider.createToken(request.getLogin());
        return new ResponseEntity<>(new AuthenticateResponseDto(request.getLogin(), token), HttpStatus.OK);
    }

    @PostMapping("/registration")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<AuthenticateResponseDto> registration(@RequestBody @Valid RegistrationUserDto userDto) {
        UserDto user = userService.add(userDto);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), userDto.getPassword()));
        String token = jwtTokenProvider.createToken(user.getLogin());
        return new ResponseEntity<>(new AuthenticateResponseDto(user.getLogin(), token), HttpStatus.OK);

    }
}
