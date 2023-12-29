package com.example.taskflow.service;


import com.example.taskflow.model.entities.RefreshToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;

public interface JWTService {
public Map<String, String> generateAccessAndRefreshToken(Authentication authentication);
public String jwtAccessTokenEncoded(String subject, Instant instant, Collection<? extends GrantedAuthority> authorities);
public String jwtRefreshTokenEncoded(String subject, Instant instant);
public Map<String,String> generateAccessTokenByRefreshToken(String refreshToken, Collection<? extends GrantedAuthority> authorities);
public RefreshToken verifyIsRevoked(RefreshToken refreshToken);
public RefreshToken verifyExpiration(RefreshToken refreshToken);
}
