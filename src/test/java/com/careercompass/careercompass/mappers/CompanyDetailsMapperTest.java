package com.careercompass.careercompass.mappers;

import com.careercompass.careercompass.dto.CompanyDetailsResponseDTO;
import com.careercompass.careercompass.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CompanyDetailsMapperTest {
    @Test
    public void testMapToCompanyDetailsResponseDTO() {
        List<City> cities = new ArrayList<>();
        List<CompanyReview> reviews = new ArrayList<>();
        cities.add(new City(1, "CityName1", new ArrayList<>()));
        cities.add(new City(2, "CityName2", new ArrayList<>()));
        reviews.add(new CompanyReview(1, "positives", "negatives", 5, 5, 5, 5, 5, new CompanyDetails(), new ApplicantDetails()));

        CompanyDetails companyDetails = new CompanyDetails(
                1,
                "companyName",
                "companySlogan",
                "companyAbout",
                "companyWebsite",
                4,
                cities,
                new User(),
                reviews
        );

        CompanyDetailsResponseDTO expectedResult = new CompanyDetailsResponseDTO(
                1,
                "companyName",
                "companySlogan",
                "companyAbout",
                "companyWebsite",
                4,
                new ArrayList<>(Arrays.asList("CityName1", "CityName2"))
        );

        CompanyDetailsMapper companyDetailsMapper = new CompanyDetailsMapper();
        CompanyDetailsResponseDTO result = companyDetailsMapper.mapToCompanyDetailsResponseDTO(companyDetails);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testMapToCompanyDetailsResponseDTO_NullInput() {
        CompanyDetailsMapper companyDetailsMapper = new CompanyDetailsMapper();
        assertNull(companyDetailsMapper.mapToCompanyDetailsResponseDTO(null));
    }
}