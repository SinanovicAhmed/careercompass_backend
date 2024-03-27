package com.careercompass.careercompass.dto;

import com.careercompass.careercompass.model.City;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailsRequestDTO {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "slogan is required")
    private String slogan;

    @NotBlank(message = "about is required")
    private String about;

    @NotBlank(message = "websiteUrl is required")
    private String websiteUrl;

    @NotNull(message = "numberOfEmployees is required")
    private Integer numberOfEmployees;

    @Valid
    @NotNull(message = "list of city ids is required")
    @Size(max = 5, message = "number of city ids must not exceed 5")
    private List<Integer> cityIds;
}
