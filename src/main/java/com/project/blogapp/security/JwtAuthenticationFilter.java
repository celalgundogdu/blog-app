package com.project.blogapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blogapp.constant.Constants;
import com.project.blogapp.exception.BusinessException;
import com.project.blogapp.repository.TokenRepository;
import com.project.blogapp.util.JwtService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService,
                                   TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String username;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith(Constants.BEARER_PREFIX)) {
                jwt = authorizationHeader.substring(7);
                username = jwtService.extractUsername(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (JwtException exception) {
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(FORBIDDEN.value());
            response.setHeader("error", exception.getMessage());
            final Map<String,String> error = new HashMap<>();
            error.put("error",exception.getMessage());
            new ObjectMapper().writeValue(response.getOutputStream(),error);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTokenValid(String jwt, UserDetails userDetails) {
        boolean isTokenValid = tokenRepository.findByToken(jwt)
                .map(token -> !(token.isExpired() || token.isRevoked()))
                .orElse(false);
        return isTokenValid && jwtService.isTokenValid(jwt, userDetails);
    }
}
