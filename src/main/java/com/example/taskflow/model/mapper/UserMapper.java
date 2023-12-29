package com.example.taskflow.model.mapper;

import com.example.taskflow.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.model.entities.UserT;

public interface UserMapper {
    UserT toUser(AuthenticationRequestDto authenticationRequestDto);
}
