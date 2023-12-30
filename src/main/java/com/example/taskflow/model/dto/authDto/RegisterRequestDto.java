package com.example.taskflow.model.dto.authDto;

import com.example.taskflow.entities.AppUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RegisterRequestDto(
        @NotBlank(message = "firstname is required")
        String firstName,
        @NotBlank(message = "lastname is required")

        String lastName,
        @Email(message = "email format is not valid")
        @NotBlank(message = "email is required")

        String email,
        @NotBlank(message = "password is required")

        String password,
        @NotNull

        Set<String> roles

) {
        public static AppUser toUser(RegisterRequestDto registerRequestDto){
                return new AppUser().builder()
                        .firstName(registerRequestDto.firstName())
                        .LastName(registerRequestDto.lastName())
                        .email(registerRequestDto.email())
                        .build();
        }

}
