package com.project.blogapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(Long userId,
                                     @JsonProperty("access_token")
                                     String accessToken,
                                     @JsonProperty("refresh_token")
                                     String refreshToken) {

    public static AuthenticationResponse convert(Long userId, String accessToken, String refreshToken) {
        return new AuthenticationResponse(userId, accessToken, refreshToken);
    }
}
