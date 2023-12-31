package com.example.taskflow.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;

public interface JWTService {
public String jwtAccessTokenEncoded(String subject, Instant instant, Collection<? extends GrantedAuthority> authorities);
public String extractUserName(String token);
public boolean isTokenValid(String jwt, UserDetails userDetails);
}
