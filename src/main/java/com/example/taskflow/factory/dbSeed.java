package com.example.taskflow.factory;

import com.example.taskflow.entities.PermissionT;
import com.example.taskflow.entities.AppRole;
import com.example.taskflow.entities.AppUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class dbSeed {
@Bean
CommandLineRunner start(){
    return args -> {
        if(true){
            List<PermissionT> permissionTList = PermissionFactory.createPermissionFactory();
            List<AppRole> appRoleList = RoleFactory.createRoles(permissionTList);
            List<AppUser> appUserList = UserFactory.createUserFactory(appRoleList);
        }
    };
}
}
