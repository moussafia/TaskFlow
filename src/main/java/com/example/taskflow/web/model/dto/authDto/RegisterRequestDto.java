package com.example.taskflow.web.model.dto.authDto;

import com.example.taskflow.entities.UserT;
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
        public static UserT toUser(RegisterRequestDto registerRequestDto){
                return new UserT().builder()
                        .firstName(registerRequestDto.firstName())
                        .LastName(registerRequestDto.lastName())
                        .email(registerRequestDto.email())
                        .build();
        }

}
