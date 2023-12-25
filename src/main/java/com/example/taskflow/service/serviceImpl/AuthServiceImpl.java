package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.domain.entities.UserT;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetailsService userDetails() {
        return (email)->{
            return (UserDetails) this.userRepository.findByEmail(email).orElseThrow(()->{
                return new UsernameNotFoundException("user with " + email + "is Not found");
            });
        };
    }
    public UserT SignUp(UserT userT){
        validateUserIfExistForSignUp(userT.getEmail());
        String passwordEncrypted = passwordEncoder.encode(userT.getPassword());
        userT.setPassword(passwordEncrypted);
        return userRepository.save(userT);
    }

    private void validateUserIfExistForSignUp(String email) {
        Optional<UserT> user = userRepository.findByEmail(email);
                if(user.isPresent()){
                    throw new RuntimeException("user with email "+ email +" already exist");
                }
    }


}
