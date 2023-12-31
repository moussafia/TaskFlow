package com.example.taskflow.service.serviceImpl.auth;

import com.example.taskflow.entities.AppUser;
import com.example.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("user with email "+ username + " not found"));
        Collection<? extends GrantedAuthority> authorities = appUser.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream().map(p->new SimpleGrantedAuthority("ROLE_" + p.getName())))
                .collect(Collectors.toSet());
        return new User(username, appUser.getPassword(), authorities);
    }
}
