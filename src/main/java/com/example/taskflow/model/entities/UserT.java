package com.example.taskflow.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Builder
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserT {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String LastName;
    @Column(unique = true)
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Collection<RoleT> roles;
    @OneToMany(mappedBy = "userT")
    @JsonBackReference
    private Collection<UserTask> userTasks;
    private int TokenPerDayAvailable;
    private int TokenPerMonthAvailable;
}
