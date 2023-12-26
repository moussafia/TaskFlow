package com.example.taskflow.configuration;

import com.example.taskflow.model.entities.UserT;
import com.example.taskflow.repository.UserRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
private RsaKeyConfig rsaKeyConfig;
private PasswordEncoder passwordEncoder;
    public SecurityConfig(RsaKeyConfig rsaKeyConfig, PasswordEncoder passwordEncoder) {
        this.rsaKeyConfig = rsaKeyConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeyConfig.publicKey()).build();
    }
    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk= new RSAKey.Builder(rsaKeyConfig.publicKey()).privateKey(rsaKeyConfig.privateKey()).build();
        JWKSource<SecurityContext> jwkSource= new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
    @Bean
    public UserDetailsService userDetailsService(){
       return new UserDetailsService() {
           @Autowired
           private UserRepository userRepository;
           @Override
           public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
               UserT userT = userRepository.findByEmail(username)
                       .orElseThrow(()-> new UsernameNotFoundException("user with email "+ username + " not found"));
               Collection<? extends GrantedAuthority> authorities = userT.getRoles().stream()
                           .flatMap(r -> r.getPermissions().stream().map(p->new SimpleGrantedAuthority(p.getName())))
                           .collect(Collectors.toSet());
               return new User(username, userT.getPassword(), authorities);
           };
       };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf->csrf.disable())
                .authorizeRequests(auth->auth.requestMatchers("api/v1/auth/**").permitAll())
                .authorizeRequests(auth->auth.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
