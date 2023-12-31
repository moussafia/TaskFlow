package com.example.taskflow.service.serviceImpl.auth.jwt;

import com.example.taskflow.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Override
    public String jwtAccessTokenEncoded(String subject, Instant instant, Collection<? extends GrantedAuthority> authorities){
        String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        return jwtEncoder
                .encode(JwtEncoderParameters.from(buildToken(subject, instant, scope)))
                .getTokenValue();
    }
    @Override
    public String extractUserName(String token){
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    @Override
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String emailUser = extractUserName(jwt);
        return (emailUser.equals(userDetails.getUsername()))
                && !isTokenExpired(jwt);
    }
    private boolean isTokenExpired(String token) {
        Jwt jwt= jwtDecoder.decode(token);
        final Instant dateExpiration = jwt.getExpiresAt();
        return dateExpiration.isBefore(Instant.now());
    }
    private JwtClaimsSet buildToken(String subject,
                                    Instant instant,
                                    String scope){
        return JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(1, ChronoUnit.MINUTES))
                .issuer("task-service")
                .claim("scope",scope)
                .claim("type_token","ACCESS_TOKEN")
                .build();
    }

}
