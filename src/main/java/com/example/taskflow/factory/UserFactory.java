package com.example.taskflow.factory;

import com.example.taskflow.entities.AppRole;
import com.example.taskflow.entities.AppUser;
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

    public static List<AppUser> createUserFactory(List<AppRole> appRoleList){
        List<AppUser> appUserList = List.of(
             new AppUser().builder().email("moha@gmail.com").firstName("moha").LastName("moha").password(passwordEncoder.encode("1234"))
                     .roles(appRoleList).build(),
             new AppUser().builder().email("msf@gmail.com").firstName("msf").LastName("msf").password(passwordEncoder.encode("1234"))
                     .roles(appRoleList.stream().filter(rl->!rl.equals("MANAGER")).toList()).build(),
             new AppUser().builder().email("moussa@gmail.com").firstName("moussa").LastName("moussa").password(passwordEncoder.encode("1234"))
                     .roles(appRoleList.stream().filter(rl->!rl.equals("MANAGER")).toList()).build()
        );
        return userRepository.saveAll(appUserList);
    }
}
