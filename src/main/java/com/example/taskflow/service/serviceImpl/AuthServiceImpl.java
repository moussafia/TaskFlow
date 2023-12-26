package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.model.entities.UserT;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.AuthService;
import com.example.taskflow.service.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetailsService userDetails() {
        return (email)->{
            return (UserDetails) this.userRepository.findByEmail(email).orElseThrow(()->{
                return new UsernameNotFoundException("user with " + email + "is Not found");
            });
        };
    }
    public UserT signUp(UserT userT){
        validateUserIfExistForSignUp(userT.getEmail());
        String passwordEncrypted = passwordEncoder.encode(userT.getPassword());
        userT.setPassword(passwordEncrypted);
        return userRepository.save(userT);
    }
    public Map<String, String> signIn(String email, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        return jwtService.generateAccessAndRefreshToken(authentication);
    }

    private void validateUserIfExistForSignUp(String email) {
        Optional<UserT> user = userRepository.findByEmail(email);
                if(user.isPresent()){
                    throw new RuntimeException("user with email "+ email +" already exist");
                }
    }


}
