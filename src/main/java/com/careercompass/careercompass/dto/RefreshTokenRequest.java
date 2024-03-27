package com.careercompass.careercompass.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "refresh token is required")
    private String token;
}
