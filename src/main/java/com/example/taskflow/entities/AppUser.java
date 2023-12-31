package com.example.taskflow.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Builder
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String LastName;
    @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Collection<AppRole> roles;
    @OneToMany(mappedBy = "appUser")
    @JsonBackReference
    private Collection<UserTask> userTasks;
    private int TokenPerDayAvailable;
    private int TokenPerMonthAvailable;
}
