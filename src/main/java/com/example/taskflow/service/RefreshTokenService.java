package com.example.taskflow.service;


import com.example.taskflow.entities.AppRole;
import com.example.taskflow.entities.RefreshToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RefreshTokenService {
public Map<String, String> generateAccessAndRefreshToken(Authentication authentication,  List<AppRole> roles);
public String jwtRefreshTokenEncoded(String subject, Instant instant);
public Map<String,String> generateAccessTokenByRefreshToken(String refreshToken);
public RefreshToken verifyIsRevoked(RefreshToken refreshToken);
public RefreshToken verifyExpiration(RefreshToken refreshToken);
}
