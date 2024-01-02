package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.Exception.CustomValidationException;
import com.example.taskflow.entities.AppUser;
import com.example.taskflow.entities.UserRequest;
import com.example.taskflow.entities.UserTask;
import com.example.taskflow.entities.enums.RequestStatus;
import com.example.taskflow.entities.enums.TaskAction;
import com.example.taskflow.repository.UserRequestRepository;
import com.example.taskflow.service.AppUserService;
import com.example.taskflow.service.UserRequestService;
import com.example.taskflow.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRequestServiceImpl implements UserRequestService {
    private final UserRequestRepository userRequestRepository;
    private final AppUserService appUserService;
    private final UserTaskService userTaskService;

    @Override
    public UserRequest createRequestForReplaceTask(Long task_id) {
        AppUser appUser = appUserService.getUserAuthenticated();
        UserTask userTask = userTaskService.getTaskForUserByTaskAction(appUser, task_id, TaskAction.ASSIGNED);
        checkTokenPerDayForUser(appUser);
        validateIfRequestAlreadyExist(userTask);
        validateDateLimitOfTask(userTask.getTask().getEndDate());
        UserRequest userRequest = new UserRequest().builder()
                .dateRequest(LocalDateTime.now())
                .requestStatus(RequestStatus.PENDING)
                .userTask(userTask)
                .build();
        return userRequestRepository.save(userRequest);
    }



    private void validateDateLimitOfTask(LocalDateTime endDateOfTask) {
        if(endDateOfTask.isBefore(LocalDateTime.now())){
            throw new CustomValidationException("You cannot make a request for a task with a deadline that has already passed.");
        }
    }

    private void checkTokenPerDayForUser(AppUser appUser) {
        if (appUser.getTokenPerDayAvailable() == 0)
            throw new CustomValidationException("You already consumed your token for today.");
    }
    private void validateIfRequestAlreadyExist(UserTask userTask) {
        userRequestRepository.findByUserTask(userTask).ifPresent(request -> {
            throw new CustomValidationException("A request already exists for the specified user task.");
        });
    }

}
