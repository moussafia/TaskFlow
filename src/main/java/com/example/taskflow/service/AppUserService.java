package com.example.taskflow.service;

import com.example.taskflow.entities.AppUser;

public interface AppUserService {
    AppUser getUserAuthenticated();
    AppUser getUserById(Long id);
    AppUser updateUser(AppUser user);
}
