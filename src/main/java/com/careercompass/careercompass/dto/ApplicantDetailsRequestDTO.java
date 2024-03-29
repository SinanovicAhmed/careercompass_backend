package com.careercompass.careercompass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantDetailsRequestDTO {
    @NotBlank(message = "firstname is required")
    private String firstname;
    @NotBlank(message = "lastname is required")
    private String lastname;
    @NotBlank(message = "phoneNumber is required")
    @Pattern(regexp="^\\+(?:[0-9] ?){6,14}[0-9]$", message="phoneNumber is in invalid format")
    private String phoneNumber;
}
