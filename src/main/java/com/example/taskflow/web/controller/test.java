package com.example.taskflow.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class test {
@GetMapping("/test")
@PreAuthorize("hasAuthority('SCOPE_ASSIGNTOOTHER')")
public ResponseEntity<String> test(){
    return ResponseEntity.ok().body("test");
}
    @GetMapping("/log")
    public void Log(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            System.out.println("Granted Authorities for user: " + authentication.getName());
            for (GrantedAuthority authority : authorities) {
                System.out.println(" - " + authority.getAuthority());
            }
        } else {
            System.out.println("No authenticated user found.");
        }
    }
}
