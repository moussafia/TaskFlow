package com.example.taskflow.model.dto.userDto;

import com.example.taskflow.entities.AppRole;
import com.example.taskflow.entities.UserTask;
import com.example.taskflow.model.dto.roleDto.RoleResponseDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
@Getter @Setter
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String LastName;
    private String email;
    private Collection<RoleResponseDto> roles;
    private int TokenPerDayAvailable;
    private int TokenPerMonthAvailable;
}
