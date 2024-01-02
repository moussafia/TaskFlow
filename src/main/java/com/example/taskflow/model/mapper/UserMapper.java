package com.example.taskflow.model.mapper;

import com.example.taskflow.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.entities.AppUser;
import com.example.taskflow.model.dto.authDto.RegisterRequestDto;
import com.example.taskflow.model.dto.userDto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {
    Class<? extends UserMapper> INSTANCE = Mappers.getMapperClass(UserMapper.class);

    UserResponseDto toUserResponseDto(AppUser user);

}
