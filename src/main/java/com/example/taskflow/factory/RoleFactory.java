package com.example.taskflow.factory;

import com.example.taskflow.model.entities.PermissionT;
import com.example.taskflow.model.entities.RoleT;
import com.example.taskflow.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RoleFactory {
    private static RoleRepository roleRepository;

    public RoleFactory(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public static List<RoleT> createRoles(List<PermissionT> permissionTList){
        List<RoleT> roleTList = List.of(
                new RoleT().builder().name("USER").permissions(permissionTList.stream()
                        .filter(p-> !p.getName().equals("can_assignToOther")).toList()).build(),
                new RoleT().builder().name("MANAGER").permissions(permissionTList).build()
        );
        return roleRepository.saveAll(roleTList);
    }
}

