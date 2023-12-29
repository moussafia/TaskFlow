package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.Exception.TokenException;
import com.example.taskflow.model.entities.RefreshToken;
import com.example.taskflow.model.entities.UserT;
import com.example.taskflow.repository.RefreshTokenRepository;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.JWTService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class JWTServiceImpl implements JWTService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;
    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;

    public JWTServiceImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder,
                          UserDetailsService userDetailsService,
                          RefreshTokenRepository refreshTokenRepository,
                          UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Map<String, String> generateAccessAndRefreshToken(Authentication authentication) {
        String subject = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Instant instant = Instant.now();
        Map<String, String> token = new HashMap<>();
        token.put("access_Token", this.jwtAccessTokenEncoded(subject, instant, authorities));
        token.put("refresh_Token", this.jwtRefreshTokenEncoded(subject, instant));
        return token;
    }
    @Override
    public String jwtAccessTokenEncoded(String subject, Instant instant, Collection<? extends GrantedAuthority> authorities){
        String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority)
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
    // @Value("${}")
    // private long refreshToken;
    @Override
    public String jwtRefreshTokenEncoded(String subject, Instant instant){
        UserT userT =userRepository.findByEmail(subject)
                .orElseThrow(() ->  new RuntimeException("resources not found"));
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);
        OffsetDateTime plusOneYear = offsetDateTime.plusYears(1);
        Instant result = plusOneYear.toInstant();
        RefreshToken refreshToken = new RefreshToken()
                .builder()
                .refreshToken(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(result)
                .revoked(false)
                .user(userT)
                .build();
        RefreshToken refreshTokenSaved = this.refreshTokenRepository.save(refreshToken);
        return refreshTokenSaved.getRefreshToken();

    }
    @Override
    public Map<String,String> generateAccessTokenByRefreshToken(String refreshToken, Collection<? extends GrantedAuthority> authorities){
        UserT user = refreshTokenRepository.findByRefreshToken(refreshToken)
                .map(this::verifyExpiration)
                .map(this::verifyIsRevoked)
                .orElseThrow(()-> new RuntimeException("refresh token not found"))
                .getUser();
        String accessToken = jwtAccessTokenEncoded(user.getEmail(), Instant.now(), authorities);
        Map<String, String> token = new HashMap<>();
        token.put("access_Token", accessToken);
        token.put("refresh_Token", refreshToken);
        return token;
    }
    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken == null){
            throw new TokenException(null, "Token is null");
        }
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            throw new TokenException(refreshToken.getRefreshToken(), "Refresh token was expired. Please make a new authentication request");
        }
        return refreshToken;
    }
    @Override
    public RefreshToken verifyIsRevoked(RefreshToken refreshToken){
        if(refreshToken.isRevoked()){
            throw new TokenException(refreshToken.getRefreshToken(), "Refresh token was expired. Please make a new authentication request");
        }
        return refreshToken;
    }
}
