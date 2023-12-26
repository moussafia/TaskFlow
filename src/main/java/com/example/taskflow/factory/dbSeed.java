package com.example.taskflow.factory;

import com.example.taskflow.model.entities.PermissionT;
import com.example.taskflow.model.entities.RoleT;
import com.example.taskflow.model.entities.UserT;
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
            List<RoleT> roleTList = RoleFactory.createRoles(permissionTList);
            List<UserT> userTList = UserFactory.createUserFactory(roleTList);
        }
    };
}
}
