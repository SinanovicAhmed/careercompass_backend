package com.careercompass.careercompass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ApplicantDetailsResponseDTO {
        private String firstname;
        private String lastname;
        private String phoneNumber;
}
