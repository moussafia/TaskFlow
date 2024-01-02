package com.example.taskflow.model.dto.permissionDto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PermissionResponseDto {
    private Long id;
    private String name;
}
