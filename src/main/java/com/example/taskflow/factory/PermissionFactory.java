package com.example.taskflow.factory;

import com.example.taskflow.entities.AppPermission;
import com.example.taskflow.repository.PermissionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PermissionFactory {
    private static PermissionRepository permissionRepository;

    public PermissionFactory(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public static List<AppPermission> createPermissionFactory(){
        List<AppPermission> appPermissionList = List.of(
                new AppPermission(null, "CREATE"),
                new AppPermission(null, "SHOW"),
                new AppPermission(null, "DELETE"),
                new AppPermission(null, "UPDATE"),
                new AppPermission(null, "ASSIGN"),
                new AppPermission(null, "ASSIGNTOOTHER"),
                new AppPermission(null, "REQUEST")
        );
        return permissionRepository.saveAll(appPermissionList);
    }
}
