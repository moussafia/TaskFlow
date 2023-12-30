package com.example.taskflow.web.model.mapper;

import com.example.taskflow.web.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.entities.UserT;

public interface UserMapper {
    UserT toUser(AuthenticationRequestDto authenticationRequestDto);
}
