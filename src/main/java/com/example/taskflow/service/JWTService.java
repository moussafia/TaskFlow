package com.example.taskflow.service;

import com.example.taskflow.entities.AppRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface JWTService {
public String jwtAccessTokenEncoded(String subject, Instant instant,
                                    Collection<? extends GrantedAuthority> authorities,
                                    List<AppRole> roles);
public String extractUserName(String token);
public boolean isTokenValid(String jwt, UserDetails userDetails);
}
