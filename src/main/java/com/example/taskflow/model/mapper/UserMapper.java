package com.example.taskflow.model.mapper;

import com.example.taskflow.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.entities.AppUser;

public interface UserMapper {
    AppUser toUser(AuthenticationRequestDto authenticationRequestDto);
}
