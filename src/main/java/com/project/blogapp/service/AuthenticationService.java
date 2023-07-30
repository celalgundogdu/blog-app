package com.project.blogapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blogapp.constant.Messages;
import com.project.blogapp.dto.request.AuthenticationRequest;
import com.project.blogapp.dto.request.RegisterRequest;
import com.project.blogapp.dto.response.AuthenticationResponse;
import com.project.blogapp.entity.Image;
import com.project.blogapp.entity.enums.Role;
import com.project.blogapp.entity.User;
import com.project.blogapp.exception.EntityNotFoundException;
import com.project.blogapp.repository.UserRepository;
import com.project.blogapp.util.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {

    private final ImageService imageService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final TokenService tokenService;


    public AuthenticationService(ImageService imageService, JwtService jwtService,
                                 AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                                 UserRepository userRepository, TokenService tokenService) {
        this.imageService = imageService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User(
                0L,
                request.getDisplayName(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(Role.USER);
        Image image = imageService.findDefaultUserImage();
        user.setImage(image);
        User savedUser = userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenService.saveUserToken(savedUser, jwt);
        return new AuthenticationResponse(savedUser.getId(), jwt, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(Messages.User.NOT_FOUND));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenService.revokeAllUserTokens(user.getId());
        tokenService.saveUserToken(user, jwt);
        return new AuthenticationResponse(user.getId(), jwt, refreshToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authorizationHeader != null) {
            refreshToken = authorizationHeader.substring(7);
            username = jwtService.extractUsername(refreshToken);
            if (username != null) {
                User user = this.userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(Messages.User.NOT_FOUND));
                if (jwtService.isTokenValid(refreshToken, user)) {
                    String accessToken = jwtService.generateToken(user);
                    AuthenticationResponse authResponse = new AuthenticationResponse(user.getId(), accessToken, refreshToken);
                    tokenService.revokeAllUserTokens(user.getId());
                    tokenService.saveUserToken(user, accessToken);
                    try {
                        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
