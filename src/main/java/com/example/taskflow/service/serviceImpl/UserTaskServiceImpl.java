package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.Exception.CustomValidationException;
import com.example.taskflow.entities.*;
import com.example.taskflow.entities.enums.RequestStatus;
import com.example.taskflow.entities.enums.TaskAction;
import com.example.taskflow.entities.enums.TaskStatus;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.repository.UserTaskRepository;
import com.example.taskflow.service.AppUserService;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserTaskServiceImpl implements UserTaskService {
    private final TaskService taskService;
    private final UserTaskRepository userTaskRepository;
    private final AppUserService appUserService;

    @Override
    public UserTask createTask(Task task, Set<Tag> tags) {
        Task taskSaved = taskService.createTask(task, tags);
        AppUser user = appUserService.getUserAuthenticated();
        UserTask userTask = new UserTask().builder()
                .appUser(user)
                .task(taskSaved)
                .taskAction(TaskAction.CREATE)
                .date(LocalDateTime.now())
                .build();
        return userTaskRepository.save(userTask);
    }
    @Override
    public UserTask updateStatusTask(Long id, TaskStatus taskStatus){
        AppUser user = appUserService.getUserAuthenticated();
        UserTask userTask = validateUserTaskOwnership(user.getId(), id);
        Task task = taskService.updateStatusTask(id, taskStatus);
        userTask.setTask(task);
        return userTaskRepository.save(userTask);
    }
    @Override
    public UserTask assignTaskToMySelf(Long task_id){
        AppUser user = appUserService.getUserAuthenticated();
        UserTask userTask = validateUserTaskOwnership(user.getId(), task_id);
        handleErrorCreationOwnerShip(userTask);
        Task task = validateTaskIfAlreadyAssignedForOwnership(userTask.getTask());
        Task taskUpdated = taskService.updateTask(task);
        UserTask userTask1 = new UserTask().builder()
                .task(taskUpdated)
                .date(LocalDateTime.now())
                .appUser(user)
                .taskAction(TaskAction.ASSIGNED)
                .build();
        return userTaskRepository.save(userTask1);
    }
    @Override
    public UserTask assignTaskToOther(Long task_id, Long user_id) {
        Task task = taskService.getTask(task_id);
        AppUser user = appUserService.getUserById(user_id);
        taskIsAlreadyAssigned(task);
        taskIsAlreadyAssigned(task);
        UserTask userTask = new UserTask().builder()
                .task(task)
                .appUser(user)
                .taskAction(TaskAction.ASSIGNED)
                .date(LocalDateTime.now())
                .build();
        return userTaskRepository.save(userTask);
    }
    @Override
    public UserTask getTaskForUserByTaskAction(AppUser user, Long task_id, TaskAction taskAction){
        Task task = taskService.getTask(task_id);
        return userTaskRepository.findByTaskAndAppUserAndTaskAction(task, user, taskAction)
                .orElseThrow(() -> {
                    if (taskAction.equals(TaskAction.ASSIGNED)) {
                        throw new CustomValidationException("The specified task is not assigned to you, or the task was not found.");
                    } else if (taskAction.equals(TaskAction.CREATE)) {
                        throw new CustomValidationException("The specified task is not created by you, or the task was not found.");
                    } else {
                        throw new CustomValidationException("Invalid TaskAction specified.");
                    }
                });
    }
    private void taskIsAlreadyAssigned(Task task){
        if(task.isAlreadyAssigned())
            throw new CustomValidationException("this task already assign to other");
    }
    @Override
    public void deleteTask(Long task_id){
        AppUser appUser = appUserService.getUserAuthenticated();
        getTaskForUserByTaskAction(appUser, task_id, TaskAction.ASSIGNED);
        try{
            getTaskForUserByTaskAction(appUser, task_id, TaskAction.CREATE);
            taskService.deleteTask(task_id);
        }catch (CustomValidationException ex){
            checkTokenPerMonthForUser(appUser);
            taskService.deleteTask(task_id);
            appUser.setTokenPerMonthAvailable(0);
            appUserService.updateUser(appUser);
        }
    }

    private void taskIsAlreadyChanged(Task task){
        if(task.isChanged())
            throw new CustomValidationException("this task already changed and assign to other");
    }
    private void checkTokenPerMonthForUser(AppUser appUser) {
        if (appUser.getTokenPerMonthAvailable() == 0)
            throw new CustomValidationException("You already consumed your token for this month.");
    }

    private UserTask validateUserTaskOwnership(Long userId, Long taskId) {
        Task task = taskService.getTask(taskId);
        UserTask userTask = userTaskRepository.findByTask(task)
                .orElseThrow(() -> new CustomValidationException("No task found for the provided task id"));
        if (!userTask.getAppUser().getId().equals(userId)) {
            throw new CustomValidationException("The authenticated user does not have ownership to update this task");
        }
        return userTask;
    }
    private void handleErrorCreationOwnerShip(UserTask userTask){
        if(!userTask.getTaskAction().equals(TaskAction.CREATE))
            throw new CustomValidationException("The authenticated user does" +
                    "not have ownership to update this task");
    }
    private Task validateTaskIfAlreadyAssignedForOwnership(Task task){
        if(task.isAlreadyAssigned())
            throw new CustomValidationException("this task is already you assigned to yourself");
        task.setAlreadyAssigned(true);
        return task;
    }
}
