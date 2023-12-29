package com.example.taskflow.service;

import com.example.taskflow.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.model.dto.authDto.AuthenticationResponseDto;
import com.example.taskflow.model.dto.authDto.RegisterRequestDto;
import com.example.taskflow.model.entities.RoleT;
import com.example.taskflow.model.entities.UserT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.Set;

public interface AuthService {
public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
public AuthenticationResponseDto signUp(RegisterRequestDto registerRequestDto);
public void validateUserIfExistForSignUp(String email);

public Set<RoleT> validateIfRoleNotExist(Set<String> roles);

public AuthenticationResponseDto generateAccessToken(Authentication authentication, UserT user);
public Map<String, String> generateAccessTokenByRefreshToken(String refreshToken);
}
