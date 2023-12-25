package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService userDetails() {
        return (email)->{
            return (UserDetails) this.userRepository.findByEmail(email).orElseThrow(()->{
                return new UsernameNotFoundException("user with " + email + "is Not found");
            });
        };
    }

}
