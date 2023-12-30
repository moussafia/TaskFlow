package com.example.taskflow.factory;

import com.example.taskflow.entities.PermissionT;
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
    public static List<AppRole> createRoles(List<PermissionT> permissionTList){
        List<AppRole> appRoleList = List.of(
                new AppRole().builder().name("USER").permissions(permissionTList.stream()
                        .filter(p-> !p.getName().equals("can_assignToOther")).toList()).build(),
                new AppRole().builder().name("MANAGER").permissions(permissionTList).build()
        );
        return roleRepository.saveAll(appRoleList);
    }
}

