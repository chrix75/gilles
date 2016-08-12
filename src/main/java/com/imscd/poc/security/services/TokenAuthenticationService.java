package com.imscd.poc.security.services;

import com.imscd.poc.exceptions.JWTTokenException;
import com.imscd.poc.security.JWTManager;
import com.imscd.poc.security.TokenHandler;
import com.imscd.poc.security.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by Christian Sperandio on 16/07/2016.
 */
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "Authorization";

    private final TokenHandler tokenHandler;

    public TokenAuthenticationService(UserService userService, JWTManager jwtManager) {
        tokenHandler = new TokenHandler(userService, jwtManager);
    }

    public Authentication getAuthentication(HttpServletRequest request) throws JWTTokenException {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final Optional<User> user = tokenHandler.parseUserFromHeader(token);
            if (user != null) {
                return new UserAuthentication(user.get());
            }
        }
        return null;
    }
}