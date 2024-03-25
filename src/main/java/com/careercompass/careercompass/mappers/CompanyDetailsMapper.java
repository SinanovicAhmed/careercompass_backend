package com.careercompass.careercompass.mappers;

import com.careercompass.careercompass.dto.CityResponseDTO;
import com.careercompass.careercompass.dto.CompanyDetailsResponseDTO;
import com.careercompass.careercompass.model.CompanyDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyDetailsMapper {
    public CompanyDetailsResponseDTO mapToCompanyDetailsResponseDTO(CompanyDetails companyDetails) {
        if(companyDetails == null) {
            return null;
        }

        CompanyDetailsResponseDTO companyDetailsResponseDTO = new CompanyDetailsResponseDTO();

        companyDetailsResponseDTO.setName(companyDetails.getName());
        companyDetailsResponseDTO.setSlogan(companyDetails.getSlogan());
        companyDetailsResponseDTO.setAbout(companyDetails.getAbout());
        companyDetailsResponseDTO.setWebsiteUrl(companyDetails.getWebsiteUrl());
        companyDetailsResponseDTO.setNumberOfEmployees(companyDetails.getNumberOfEmployees());

        List<CityResponseDTO> cityResponseDTOs = companyDetails.getCities().stream()
                .map(city -> new CityResponseDTO(city.getName()))
                .toList();

        companyDetailsResponseDTO.setCities(cityResponseDTOs);

        return companyDetailsResponseDTO;
    }
}
