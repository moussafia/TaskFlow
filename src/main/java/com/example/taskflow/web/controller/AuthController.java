package com.example.taskflow.web.controller;

import com.example.taskflow.model.dto.authDto.AuthLogInDto;
import com.example.taskflow.model.mapper.UserMapper;
import com.example.taskflow.service.AuthService;
import com.example.taskflow.service.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @PostMapping("/logIn")
    public ResponseEntity<Map<String,String>> logIn(@RequestBody AuthLogInDto authLogInDto){
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authLogInDto.email(), authLogInDto.password())
        );

        Map<String, String> token = jwtService.generateAccessAndRefreshToken(authentication);
        return ResponseEntity.ok().body(token);

    }
}
