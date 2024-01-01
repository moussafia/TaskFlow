package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.Exception.CustomValidationException;
import com.example.taskflow.entities.AppUser;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final UserRepository userRepository;
    @Override
    public AppUser getUserAuthenticated() {
        String emailUser = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(emailUser)
                .orElseThrow(()-> new CustomValidationException("email not found"));
    }

    @Override
    public AppUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new CustomValidationException("no user found for the provided id"));
    }

    @Override
    public AppUser updateUser(AppUser user) {
        getUserById(user.getId());
        return userRepository.save(user);
    }
}
