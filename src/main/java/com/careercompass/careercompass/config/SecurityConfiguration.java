package com.careercompass.careercompass.config;

import com.careercompass.careercompass.model.Role;
import com.careercompass.careercompass.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final UserService userService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/**", "/error").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/company/details/**").hasAuthority(Role.COMPANY.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/applicant/details/**").hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/applicant/details/**").hasAnyAuthority(Role.APPLICANT.name(), Role.COMPANY.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/company/details/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/company/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/company/reviews/**").hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/company/reviews/**").hasAuthority(Role.APPLICANT.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore
                        (jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
