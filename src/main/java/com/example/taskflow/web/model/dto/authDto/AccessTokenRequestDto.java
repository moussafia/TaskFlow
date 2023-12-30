package com.example.taskflow.web.model.dto.authDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccessTokenRequestDto(
    @NotBlank
    @NotNull(message = "refresh token is valid")
    String refreshToken){
}
