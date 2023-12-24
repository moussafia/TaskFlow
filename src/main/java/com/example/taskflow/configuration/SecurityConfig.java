package com.example.taskflow.configuration;

import com.example.taskflow.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
private RsaKeyConfig rsaKeyConfig;
private PasswordEncoder passwordEncoder;
private AuthService authService;
    public SecurityConfig(RsaKeyConfig rsaKeyConfig, PasswordEncoder passwordEncoder, AuthService authService) {
        this.rsaKeyConfig = rsaKeyConfig;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(this.authService.userDetails());
        return new ProviderManager(authProvider);
    }

    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return null;
    }



}
