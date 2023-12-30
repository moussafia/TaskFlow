package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.service.JWTService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;

import javax.security.auth.Subject;
import java.util.function.Function;

public class JWTServiceImpl implements JWTService {

    public String extractUserName(String token){
        return
    }
    private <T> T extractClaim(String token, Function<OAuth2AuthenticatedPrincipal, T> claimsResolver) {
        final OAuth2AuthenticatedPrincipal claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private OAuth2AuthenticatedPrincipal extractAllClaims(String token) {

    }
}
