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
    @NotBlank
    private String name;

    @NotBlank
    private String slogan;

    @NotBlank
    private String about;

    @NotBlank
    private String websiteUrl;

    @NotNull
    private Integer numberOfEmployees;

    @Valid
    @NotNull
    @Size(max = 5)
    private List<Integer> cityIds;
}
