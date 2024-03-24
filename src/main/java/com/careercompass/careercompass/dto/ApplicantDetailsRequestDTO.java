package com.careercompass.careercompass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantDetailsRequestDTO {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Pattern(regexp="^\\+(?:[0-9] ?){6,14}[0-9]$", message="Invalid phone number format")
    private String phoneNumber;
}
