package com.example.taskflow.factory;

import com.example.taskflow.entities.RoleT;
import com.example.taskflow.entities.UserT;
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
             new UserT().builder().email("moha@gmail.com").firstName("moha").LastName("moha").password(passwordEncoder.encode("1234"))
                     .roles(roleTList).build(),
             new UserT().builder().email("msf@gmail.com").firstName("msf").LastName("msf").password(passwordEncoder.encode("1234"))
                     .roles(roleTList.stream().filter(rl->!rl.equals("MANAGER")).toList()).build(),
             new UserT().builder().email("moussa@gmail.com").firstName("moussa").LastName("moussa").password(passwordEncoder.encode("1234"))
                     .roles(roleTList.stream().filter(rl->!rl.equals("MANAGER")).toList()).build()
        );
        return userRepository.saveAll(userTList);
    }
}
