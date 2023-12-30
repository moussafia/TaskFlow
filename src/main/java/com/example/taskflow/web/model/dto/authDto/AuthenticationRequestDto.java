package com.example.taskflow.web.model.dto.authDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationRequestDto(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Email cannot be blank")
        @Size(min = 2, message = "Password must be at least 2 characters long")
        String password
) {
}
