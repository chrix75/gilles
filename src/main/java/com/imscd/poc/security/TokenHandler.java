package com.imscd.poc.security;

import com.imscd.poc.domain.ResponseAuthorities;
import com.imscd.poc.exceptions.JWTTokenException;
import com.imscd.poc.security.services.UserService;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Christian Sperandio on 16/07/2016.
 */
public final class TokenHandler {

    private final String marker = "Bearer ";
    private final UserService userService;
    private final JWTManager jwtManager;

    public TokenHandler(UserService userService, JWTManager jwtManager) {
        this.userService = userService;
        this.jwtManager = jwtManager;
    }

    public Optional<User> parseUserFromHeader(String header) throws JWTTokenException {

        if (!header.startsWith(marker)) {
            return Optional.empty();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", header);
        headers.set("application-code", "clotho");

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<ResponseAuthorities> responseEntity = restTemplate.exchange("http://localhost:1111/permissions/company/4b21f7db-b0a1-47a3-9007-a0547c7104cd/application/ddi/user/peter_parker",
                HttpMethod.GET, entity, ResponseAuthorities.class);


        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new JWTTokenException("Clotho communication failed.");
        }

        List<GrantedAuthority> authorities = responseEntity.getBody().getAuthorities()
                .stream()
                .map(x -> new SimpleGrantedAuthority(x))
                .collect(Collectors.toList());

        User user = new User(responseEntity.getBody().getLogin(), "", authorities);

        return Optional.of(user);
    }
}