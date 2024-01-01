package com.example.taskflow.service;

import com.example.taskflow.entities.UserRequest;

public interface UserRequestService {
    public UserRequest createRequestForReplaceTask(Long task_id);
}
