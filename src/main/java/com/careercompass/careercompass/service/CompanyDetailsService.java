package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.*;
import com.careercompass.careercompass.exception.UnauthorizedToUpdateException;
import com.careercompass.careercompass.mappers.CompanyDetailsMapper;
import com.careercompass.careercompass.model.City;
import com.careercompass.careercompass.model.CompanyDetails;
import com.careercompass.careercompass.repository.CityRepository;
import com.careercompass.careercompass.repository.CompanyDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CompanyDetailsService {
    private final CompanyDetailsRepository companyDetailsRepository;
    private final CityRepository cityRepository;
    private final CompanyDetailsMapper companyDetailsMapper;
    private final AuthorizeUser authorizeUser;

    public CompanyDetailsResponseDTO findByUserID(Integer id) {
        CompanyDetails companyDetails = companyDetailsRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Details of company with ID " + id + " not found"));

        return companyDetailsMapper.mapToCompanyDetailsResponseDTO(companyDetails);
    }

    public CompanyDetailsResponseDTO updateByUserID(Integer id, CompanyDetailsRequestDTO newCompanyDetails) {
        CompanyDetails companyDetails = companyDetailsRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Details of company with ID " + id + " not found"));

        if(!authorizeUser.isAuthorizedForChange(companyDetails.getUser().getUsername())) {
            throw new UnauthorizedToUpdateException("You don't have permission to do update details of this user");
        }

        companyDetails.setName(newCompanyDetails.getName());
        companyDetails.setSlogan(newCompanyDetails.getSlogan());
        companyDetails.setAbout(newCompanyDetails.getAbout());
        companyDetails.setWebsiteUrl(newCompanyDetails.getWebsiteUrl());
        companyDetails.setNumberOfEmployees(newCompanyDetails.getNumberOfEmployees());

        List<City> cities = new ArrayList<>();
        if (newCompanyDetails.getCityIds() != null) {
            cities = newCompanyDetails.getCityIds().stream()
                    .map(cityId -> cityRepository.findById(cityId)
                            .orElseThrow(() -> new EntityNotFoundException("City with ID " + cityId + " not found")))
                    .collect(Collectors.toList());
        }

        companyDetails.setCities(cities);

        CompanyDetails updatedCompanyDetails = companyDetailsRepository.save(companyDetails);
        return companyDetailsMapper.mapToCompanyDetailsResponseDTO(updatedCompanyDetails);
    }
}
