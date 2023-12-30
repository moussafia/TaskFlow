package com.example.taskflow.web.controller;

import com.example.taskflow.model.dto.authDto.AccessTokenRequestDto;
import com.example.taskflow.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.model.dto.authDto.AuthenticationResponseDto;
import com.example.taskflow.model.dto.authDto.RegisterRequestDto;
import com.example.taskflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/logIn")
    public ResponseEntity<AuthenticationResponseDto> logIn(@RequestBody
                                                               @Valid AuthenticationRequestDto authenticationRequestDto){
        return ResponseEntity.ok(authService.authenticate(authenticationRequestDto));

    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> logIn(@RequestBody
                                                               @Valid RegisterRequestDto registerRequestDto){
        return ResponseEntity.ok(authService.signUp(registerRequestDto));
    }
    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> getAccessTokenByRefreshToken(@RequestBody
                                                                            @Valid AccessTokenRequestDto refreshToken){

        return ResponseEntity.ok(authService.generateAccessTokenByRefreshToken(refreshToken.refreshToken()));
    }
}
