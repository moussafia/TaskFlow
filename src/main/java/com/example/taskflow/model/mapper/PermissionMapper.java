package com.example.taskflow.model.mapper;

import com.example.taskflow.model.dto.permissionDto.PermissionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.security.Permission;

@Mapper
public interface PermissionMapper {
    Class<? extends PermissionMapper> INSTANCE = Mappers.getMapperClass(PermissionMapper.class);
    PermissionResponseDto permissionToPermissionDto(Permission permission);
}
