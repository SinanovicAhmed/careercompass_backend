package com.careercompass.careercompass.dto;

import com.careercompass.careercompass.model.Role;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private Role role;
}
