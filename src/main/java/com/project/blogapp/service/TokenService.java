package com.project.blogapp.service;

import com.project.blogapp.entity.Token;
import com.project.blogapp.entity.User;
import com.project.blogapp.entity.enums.TokenType;
import com.project.blogapp.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void saveUserToken(User user, String jwt) {
        Token token = new Token(jwt, TokenType.BEARER, user);
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(Long userId) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(userId);
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }
}
