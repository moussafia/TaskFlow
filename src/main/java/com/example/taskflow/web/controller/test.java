package com.example.taskflow.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    Logger logger = LoggerFactory.getLogger(test.class);
@GetMapping("/test")
@PreAuthorize("hasRole('ASSIGNTOOTHER')")
public ResponseEntity<String> test(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    logger.info("User Authorities: {}", authentication.getAuthorities());
    return ResponseEntity.ok().body("test");
    }
}
