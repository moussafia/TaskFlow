package com.example.taskflow.factory;

import com.example.taskflow.model.entities.RoleT;
import com.example.taskflow.model.entities.UserT;
import com.example.taskflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserFactory {
    private static UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;

    public UserFactory(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static List<UserT> createUserFactory(List<RoleT> roleTList){
        List<UserT> userTList = List.of(
                new UserT(null, "mohammed", "moussafia", "moha@gmail.com",passwordEncoder.encode("1234"),roleTList, null,null),
                new UserT(null, "simo", "moussa", "simo@gmail.com",passwordEncoder.encode("1234"),roleTList.stream().filter(rl->!rl.equals("MANAGER")).toList(), null, null),
                new UserT(null, "bilal", "bilal", "bilal@gmail.com",passwordEncoder.encode("1234"),roleTList.stream().filter(rl->!rl.equals("MANAGER")).toList(), null, null)
        );
        return userRepository.saveAll(userTList);
    }
}
