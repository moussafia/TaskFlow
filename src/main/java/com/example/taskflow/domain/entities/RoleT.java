package com.example.taskflow.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleT {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    @ManyToMany
    private Collection<PermissionT> permissions;
}
