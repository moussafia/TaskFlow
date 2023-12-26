package com.example.taskflow.factory;

import com.example.taskflow.model.entities.PermissionT;
import com.example.taskflow.repository.PermissionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PermissionFactory {
    private static PermissionRepository permissionRepository;

    public PermissionFactory(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public static List<PermissionT> createPermissionFactory(){
        List<PermissionT> permissionTList = List.of(
                new PermissionT(null,"can_create"),
                new PermissionT(null,"can_show"),
                new PermissionT(null,"can_delete"),
                new PermissionT(null,"can_update"),
                new PermissionT(null,"can_assign"),
                new PermissionT(null,"can_assignToOther"),
                new PermissionT(null,"can_request")
        );
        return permissionRepository.saveAll(permissionTList);
    }
}
