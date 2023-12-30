package com.example.taskflow.web.model.dto.errorDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data @AllArgsConstructor @NoArgsConstructor
public class Response<T> {
    private String message;
    private T result;
}
