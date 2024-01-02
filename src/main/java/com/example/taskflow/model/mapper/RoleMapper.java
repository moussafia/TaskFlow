package com.example.taskflow.model.mapper;

import com.example.taskflow.model.dto.roleDto.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.management.relation.Role;

@Mapper
public interface RoleMapper {
    Class<? extends RoleMapper> INSTANCE = Mappers.getMapperClass(RoleMapper.class);

    RoleResponseDto roleToRoleResponseDto(Role role);
}
