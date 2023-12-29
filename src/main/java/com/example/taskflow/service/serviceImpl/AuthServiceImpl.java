package com.example.taskflow.service.serviceImpl;

import com.example.taskflow.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.model.dto.authDto.AuthenticationResponseDto;
import com.example.taskflow.model.dto.authDto.RegisterRequestDto;
import com.example.taskflow.model.entities.RoleT;
import com.example.taskflow.model.entities.UserT;
import com.example.taskflow.repository.RefreshTokenRepository;
import com.example.taskflow.repository.RoleRepository;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.AuthService;
import com.example.taskflow.service.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    private RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JWTService jwtService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDto.email(), authenticationRequestDto.password())
        );
        var user = userRepository.findByEmail(authenticationRequestDto.email()).get();
        return generateAccessToken(authentication, user);
    }
    @Override
    public AuthenticationResponseDto signUp(RegisterRequestDto registerRequestDto){
        validateUserIfExistForSignUp(registerRequestDto.email());
        Set<RoleT> roles = validateIfRoleNotExist(registerRequestDto.roles());
        String passwordEncrypted = passwordEncoder.encode(registerRequestDto.password());
        UserT userT = RegisterRequestDto.toUser(registerRequestDto);
        userT.setPassword(passwordEncrypted);
        userT.setRoles(roles);
        var userSaved = userRepository.save(userT);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getEmail(), userSaved.getPassword());
        return generateAccessToken(authentication, userSaved);
    }

    @Override
    public void validateUserIfExistForSignUp(String email) {
        Optional<UserT> user = userRepository.findByEmail(email);
                if(user.isPresent()){
                    throw new RuntimeException("user with email "+ email +" already exist");
                }
    }
    @Override
    public Set<RoleT> validateIfRoleNotExist(Set<String> roles){
        return roles.stream()
                .map(r -> roleRepository.findByName(r)
                .orElseThrow(() -> new RuntimeException("role with name " + r + " not found,please create one")))
                .collect(Collectors.toSet());
    }

    @Override
    public AuthenticationResponseDto generateAccessToken(Authentication authentication, UserT user){
        Map<String, String> token = jwtService.generateAccessAndRefreshToken(authentication);
        return  new AuthenticationResponseDto(
                user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getRoles().stream().map(RoleT::getName).collect(Collectors.toList()),
                token.get("access_Token"), token.get("refresh_Token")
        );
    }
    @Override
    public Map<String, String> generateAccessTokenByRefreshToken(String refreshToken){
        return jwtService.generateAccessTokenByRefreshToken(refreshToken);
    }



}
