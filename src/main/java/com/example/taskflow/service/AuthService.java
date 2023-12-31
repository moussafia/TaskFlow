package com.example.taskflow.service;

import com.example.taskflow.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.model.dto.authDto.AuthenticationResponseDto;
import com.example.taskflow.model.dto.authDto.RegisterRequestDto;
import com.example.taskflow.entities.AppRole;
import com.example.taskflow.entities.AppUser;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

public interface AuthService {
public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
public AuthenticationResponseDto signUp(RegisterRequestDto registerRequestDto);

}
