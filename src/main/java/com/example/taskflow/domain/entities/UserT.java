package com.example.taskflow.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Setter @Getter
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
}
