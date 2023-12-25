package com.example.taskflow.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {


    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("test");
    }
}
