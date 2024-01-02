package com.example.taskflow.model.mapper;

import com.example.taskflow.entities.Tag;
import com.example.taskflow.entities.Task;
import com.example.taskflow.model.dto.taskDto.CreateTaskRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;

@Mapper
public interface TaskMapper {
    Class<? extends TaskMapper> INSTANCE = Mappers.getMapperClass(TaskMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isChanged", ignore = true)
    @Mapping(target = "isAlreadyAssigned", ignore = true)
    @Mapping(target = "userTasks", ignore = true)
    @Mapping(target = "taskStatus", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Task createTaskRequestToTask(CreateTaskRequest createTaskRequest);

}
