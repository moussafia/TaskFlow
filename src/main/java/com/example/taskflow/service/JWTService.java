package com.example.taskflow.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;

public interface JWTService {
public Map<String, String> generateAccessToken(Authentication authentication);
public Map<String, String> generateRefreshToken();
public String jwtAccessTokenEncoded(String subject, Instant instant, Collection<? extends GrantedAuthority> authorities);
public String jwtRefreshTokenEncoded(String subject, Instant instant);
}
