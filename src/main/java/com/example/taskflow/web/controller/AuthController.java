package com.example.taskflow.web.controller;

import com.example.taskflow.model.dto.authDto.AuthLogInDto;
import com.example.taskflow.model.entities.UserT;
import com.example.taskflow.model.mapper.UserMapper;
import com.example.taskflow.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private AuthService authService;
    private UserMapper userMapper;

    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }
    @GetMapping("/logIn")
    public ResponseEntity<Map<String,String>> logIn(@RequestBody AuthLogInDto authLogInDto){
        UserT userT = userMapper.toUser(authLogInDto);
        return null;

    }
}
