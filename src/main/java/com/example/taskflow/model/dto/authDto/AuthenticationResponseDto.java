package com.example.taskflow.model.dto.authDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AuthenticationResponseDto(
        Long id,
        String firstName,

        String LastName,
        String email,
        List<String> roles,

        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
