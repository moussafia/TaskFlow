package com.example.taskflow.service.serviceImpl.auth;

import com.example.taskflow.model.dto.authDto.AuthenticationRequestDto;
import com.example.taskflow.model.dto.authDto.AuthenticationResponseDto;
import com.example.taskflow.model.dto.authDto.RegisterRequestDto;
import com.example.taskflow.entities.AppRole;
import com.example.taskflow.entities.AppUser;
import com.example.taskflow.repository.RoleRepository;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.AuthService;
import com.example.taskflow.service.RefreshTokenService;
import jakarta.validation.ValidationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private RefreshTokenService refreshTokenService;
    private RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           RefreshTokenService refreshTokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
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
        Set<AppRole> roles = validateIfRoleNotExist(registerRequestDto.roles());
        String passwordEncrypted = passwordEncoder.encode(registerRequestDto.password());
        AppUser appUser = RegisterRequestDto.toUser(registerRequestDto);
        appUser.setPassword(passwordEncrypted);
        appUser.setRoles(roles);
        var userSaved = userRepository.save(appUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getEmail(), userSaved.getPassword());
        return generateAccessToken(authentication, userSaved);
    }

    private void validateUserIfExistForSignUp(String email) {
        Optional<AppUser> user = userRepository.findByEmail(email);
                if(user.isPresent()){
                    throw new ValidationException("user with email "+ email +" already exist");
                }
    }
    private Set<AppRole> validateIfRoleNotExist(Set<String> roles){
        return roles.stream()
                .map(r -> roleRepository.findByName(r)
                .orElseThrow(() -> new ValidationException("role with name " + r + " not found,please create one")))
                .collect(Collectors.toSet());
    }

    private AuthenticationResponseDto generateAccessToken(Authentication authentication, AppUser user){
        Map<String, String> token = refreshTokenService.generateAccessAndRefreshToken(authentication,
                                        user.getRoles().stream().toList());
        return  new AuthenticationResponseDto(
                user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getRoles().stream().map(AppRole::getName).collect(Collectors.toList()),
                token.get("access_Token"), token.get("refresh_Token")
        );
    }
//    private Map<String, String> generateAccessTokenByRefreshToken(String refreshToken){
//        return refreshTokenService.generateAccessTokenByRefreshToken(refreshToken);
//    }



}
