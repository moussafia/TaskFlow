package com.example.taskflow.factory;

import com.example.taskflow.entities.AppPermission;
import com.example.taskflow.entities.AppRole;
import com.example.taskflow.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RoleFactory {
    private static RoleRepository roleRepository;

    public RoleFactory(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public static List<AppRole> createRoles(List<AppPermission> appPermissionList){
        List<AppRole> appRoleList = List.of(
                new AppRole().builder().name("USER").permissions(appPermissionList.stream()
                        .filter(p-> !p.getName().equals("ASSIGNTOOTHER")).toList()).build(),
                new AppRole().builder().name("MANAGER").permissions(appPermissionList).build()
        );
        return roleRepository.saveAll(appRoleList);
    }
}

