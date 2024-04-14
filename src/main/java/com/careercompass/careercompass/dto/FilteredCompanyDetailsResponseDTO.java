package com.careercompass.careercompass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilteredCompanyDetailsResponseDTO {
        private List<CompanyDetailsResponseDTO> companies;
        private boolean hasNextPage;
}
