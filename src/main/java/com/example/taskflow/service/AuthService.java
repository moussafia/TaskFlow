package com.example.taskflow.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService {
    public UserDetailsService userDetails();
}
