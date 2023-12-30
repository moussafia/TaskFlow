package com.example.taskflow.handler;

import com.example.taskflow.Exception.TokenException;
import com.example.taskflow.model.dto.errorDto.ErrorResponse;
import com.example.taskflow.model.dto.errorDto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException te, WebRequest webRequest){
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .error("Invalid Token")
                .status(HttpStatus.FORBIDDEN.value())
                .message(te.getMessage())
                .path(webRequest.getDescription(false))
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleRunTimeException(RuntimeException re){
        Map<String, String> error = new HashMap<>();
        error.put("error", "error server");
        error.put("message", re.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleMethodValidationExceptions(MethodArgumentNotValidException me){
        Map<String, String> errors = new HashMap<>();
        me.getBindingResult().getFieldErrors().forEach(fieldError ->{
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ResponseEntity<Response<Object>> handleValidationExceptions(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.internalServerError().body(Response.builder()
                .message("Internal server error")
                .result(ex.getMessage())
                .build());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                .message(ex.getMessage())
                .build());
    }
}
