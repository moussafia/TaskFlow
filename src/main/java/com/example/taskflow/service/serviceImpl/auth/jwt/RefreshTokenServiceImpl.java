package com.example.taskflow.service.serviceImpl.auth.jwt;

import com.example.taskflow.Exception.TokenException;
import com.example.taskflow.entities.RefreshToken;
import com.example.taskflow.entities.AppUser;
import com.example.taskflow.repository.RefreshTokenRepository;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.JWTService;
import com.example.taskflow.service.RefreshTokenService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;
    private JWTService jwtService;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   UserRepository userRepository,
                                   JWTService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    @Override
    public Map<String, String> generateAccessAndRefreshToken(Authentication authentication) {
        String subject = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Instant instant = Instant.now();
        Map<String, String> token = new HashMap<>();
        token.put("access_Token", jwtService.jwtAccessTokenEncoded(subject, instant, authorities));
        token.put("refresh_Token", this.jwtRefreshTokenEncoded(subject, instant));
        return token;
    }

    @Override
    public String jwtRefreshTokenEncoded(String subject, Instant instant){
        AppUser appUser =userRepository.findByEmail(subject)
                .orElseThrow(() ->  new RuntimeException("resources not found"));
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);
        OffsetDateTime plusOneYear = offsetDateTime.plusYears(1);
        Instant result = plusOneYear.toInstant();
        RefreshToken refreshToken = new RefreshToken()
                .builder()
                .refreshToken(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(result)
                .revoked(false)
                .user(appUser)
                .build();
        RefreshToken refreshTokenSaved = this.refreshTokenRepository.save(refreshToken);
        return refreshTokenSaved.getRefreshToken();

    }
    @Override
    public Map<String,String> generateAccessTokenByRefreshToken(String refreshToken){
        AppUser user = refreshTokenRepository.findByRefreshToken(refreshToken)
                .map(this::verifyExpiration)
                .map(this::verifyIsRevoked)
                .orElseThrow(()-> new RuntimeException("refresh token not found"))
                .getUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        String accessToken = jwtService.jwtAccessTokenEncoded(user.getEmail(), Instant.now(), authentication.getAuthorities());
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
