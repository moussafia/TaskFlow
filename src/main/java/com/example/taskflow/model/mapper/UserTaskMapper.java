package com.example.taskflow.model.mapper;

import com.example.taskflow.entities.UserTask;
import com.example.taskflow.model.dto.userTaskDto.UserTaskResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserTaskMapper {
    Class<? extends UserTaskMapper> INSTANCE = Mappers.getMapperClass(UserTaskMapper.class);
   UserTaskResponseDto toUserTaskDto(UserTask userTask);
}
