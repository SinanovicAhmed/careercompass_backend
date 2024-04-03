package com.careercompass.careercompass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailsResponseDTO {
    private String name;
    private String slogan;
    private String about;
    private String websiteUrl;
    private Integer numberOfEmployees;
    private List<String> cities;
    private List<CompanyReviewResponseDTO> reviews;
}
