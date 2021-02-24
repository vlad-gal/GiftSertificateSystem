package com.epam.esm.filter;

import com.epam.esm.dto.UserDto;
import com.epam.esm.security.AuthenticationDetails;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserAccessFilter extends GenericFilterBean {
    private static final String REGEX = "(/users/[1-9]\\d*)(/orders(/[1-9]\\d*)?)?";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && uri.matches(REGEX)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();
            if (Long.parseLong(uri.split("/")[2]) != details.getId() && !details.isAdmin()) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
