package com.project.blogapp.security;

import com.project.blogapp.constant.Constants;
import com.project.blogapp.entity.Token;
import com.project.blogapp.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    public LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        if (authorizationHeader != null && authorizationHeader.startsWith(Constants.BEARER_PREFIX)) {
            jwt = authorizationHeader.substring(7);
            Token storedToken = tokenRepository.findByToken(jwt).orElse(null);
            if (storedToken != null) {
                storedToken.setExpired(true);
                storedToken.setRevoked(true);
                tokenRepository.save(storedToken);
            }
        }
    }
}
