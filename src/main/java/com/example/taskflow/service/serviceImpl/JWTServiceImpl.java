package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.service.JWTService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class JWTServiceImpl implements JWTService {
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    public JWTServiceImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Map<String, String> generateAccessToken(Authentication authentication) {
        String subject = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Instant instant = Instant.now();
        Map<String, String> token = new HashMap<>();
        token.put("acces Token", this.jwtAccessTokenEncoded(subject, instant, authorities));
        token.put("refresh Token", this.jwtRefreshTokenEncoded(subject, instant));
        return token;
    }
    @Override
    public String jwtAccessTokenEncoded(String subject, Instant instant, Collection<? extends GrantedAuthority> authorities){
        String scope = authorities.stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsAccessToken = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(1, ChronoUnit.MINUTES))
                .issuer("task-service")
                .claim("scope",scope)
                .claim("type_token","ACCESS_TOKEN")
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsAccessToken)).getTokenValue();
    }
    @Override
    public String jwtRefreshTokenEncoded(String subject, Instant instant){
        JwtClaimsSet jwtClaimsRefreshToken = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(1, ChronoUnit.MINUTES))
                .issuer("task-service")
                .claim("type_token","REFRESH_TOKEN")
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsRefreshToken)).getTokenValue();

    }

    @Override
    public Map<String, String> generateRefreshToken() {
        return null;
    }
}
