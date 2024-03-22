package com.careercompass.careercompass.controller;

import com.careercompass.careercompass.dto.RefreshTokenRequest;
import com.careercompass.careercompass.dto.SignInRequest;
import com.careercompass.careercompass.dto.JwtAuthenticationResponse;
import com.careercompass.careercompass.dto.SignUpRequest;
import com.careercompass.careercompass.model.User;
import com.careercompass.careercompass.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(authService.usernameExists(signUpRequest.getUsername())) {
            return new ResponseEntity<>("That username already exists", HttpStatus.CONFLICT);
        }

        if (!authService.signUpSuccesful(signUpRequest)) {
            return new ResponseEntity<>("Something went wrong. User is not saved.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("User is saved to the database successfully", HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        if(!authService.usernameExists(signInRequest.getUsername())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!authService.userAuthenticated(signInRequest.getUsername(),signInRequest.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(authService.signIn(signInRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }
}
