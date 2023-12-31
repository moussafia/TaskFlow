package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.entities.AppUser;
import com.example.taskflow.entities.Task;
import com.example.taskflow.entities.UserTask;
import com.example.taskflow.entities.enums.TaskAction;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.repository.UserTaskRepository;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserTaskServiceImpl implements UserTaskService {
    private final TaskService taskService;
    private final UserTaskRepository userTaskRepository;
    private final UserRepository userRepository;

    @Override
    public UserTask createTask(Task task) {
        Task taskSaved = taskService.createTask(task);
        UserTask userTask = new UserTask().builder()
                .appUser(getUserAuthenticated())
                .task(taskSaved)
                .taskAction(TaskAction.CREATE)
                .date(LocalDateTime.now())
                .build();
        return userTaskRepository.save(userTask);
    }
    private AppUser getUserAuthenticated(){
        String emailUser = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(emailUser)
                .orElseThrow(()-> new RuntimeException("email not found"));
    }
}
