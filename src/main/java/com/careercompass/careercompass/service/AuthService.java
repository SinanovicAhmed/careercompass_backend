package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.RefreshTokenRequest;
import com.careercompass.careercompass.dto.SignInRequest;
import com.careercompass.careercompass.dto.JwtAuthenticationResponse;
import com.careercompass.careercompass.dto.SignUpRequest;
import com.careercompass.careercompass.model.ApplicantDetails;
import com.careercompass.careercompass.model.CompanyDetails;
import com.careercompass.careercompass.model.Role;
import com.careercompass.careercompass.model.User;
import com.careercompass.careercompass.repository.ApplicantDetailsRepository;
import com.careercompass.careercompass.repository.CompanyDetailsRepository;
import com.careercompass.careercompass.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final ApplicantDetailsRepository applicantDetailsRepository;
    private final CompanyDetailsRepository companyDetailsRepository;

    @Transactional
    public Boolean signUpSuccesful(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(signUpRequest.getRole());

        User savedUser = userRepository.save(user);

        if(signUpRequest.getRole() == Role.APPLICANT) {
            ApplicantDetails applicantDetails = new ApplicantDetails();
            applicantDetails.setUser(savedUser);
            ApplicantDetails savedApplicantDetails = applicantDetailsRepository.save(applicantDetails);

            return savedUser.getId() != null && savedApplicantDetails.getId() != null;
        }else {
            CompanyDetails companyDetails = new CompanyDetails();
            companyDetails.setUser(savedUser);
            CompanyDetails savedCompanyDetails = companyDetailsRepository.save(companyDetails);

            return savedUser.getId() != null && savedCompanyDetails.getId() != null;
        }
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword())
        );

        var user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(username).orElseThrow();

        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }

        return null;
    }

    public boolean usernameExists(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    public boolean userAuthenticated(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return passwordEncoder.matches(password, user.get().getPassword());
        }
        return false;
    }
}
