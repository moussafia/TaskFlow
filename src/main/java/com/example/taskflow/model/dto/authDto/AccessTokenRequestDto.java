package com.example.taskflow.model.dto.authDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccessTokenRequestDto(
    @NotBlank
    @NotNull(message = "refresh token is valid")
    String refreshToken){
}
