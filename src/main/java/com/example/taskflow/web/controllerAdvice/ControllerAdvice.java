package com.example.taskflow.web.controllerAdvice;

import com.example.taskflow.Exception.TokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

//    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Map<String,String>> handleExceptions(Throwable th){
//        Map<String, String> error = new HashMap<>();
//        error.put("error", "Internal Server Error");
//        error.put("message", th.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String,String>> handleTokenException(TokenException te){
        Map<String, String> error = new HashMap<>();
        error.put("error","token invalid");
        error.put("message",te.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleRunTimeException(RuntimeException re){
        Map<String, String> error = new HashMap<>();
        error.put("error", "error server");
        error.put("message", re.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
