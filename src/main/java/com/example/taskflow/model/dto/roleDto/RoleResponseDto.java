package com.example.taskflow.model.dto.roleDto;

import com.example.taskflow.model.dto.permissionDto.PermissionResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter @Setter
public class RoleResponseDto {
    private String id;
    private String name;
    private Collection<PermissionResponseDto> permissions;
}
