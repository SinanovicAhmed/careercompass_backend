package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.CompanyDetailsRequestDTO;
import com.careercompass.careercompass.dto.CompanyDetailsResponseDTO;
import com.careercompass.careercompass.exception.UnauthorizedToUpdateException;
import com.careercompass.careercompass.mappers.CompanyDetailsMapper;
import com.careercompass.careercompass.model.City;
import com.careercompass.careercompass.model.CompanyDetails;
import com.careercompass.careercompass.model.User;
import com.careercompass.careercompass.repository.CompanyDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDetailsServiceTest {
    CompanyDetails companyDetails;
    CompanyDetails companyDetailsFilled;
    CompanyDetailsResponseDTO companyDetailsResponseDTO;
    CompanyDetailsRequestDTO companyDetailsRequestDTO;

    @InjectMocks
    private CompanyDetailsService companyDetailsService;
    @Mock
    private CompanyDetailsRepository companyDetailsRepository;
    @Mock
    private CompanyDetailsMapper companyDetailsMapper;

    @Mock
    private AuthorizeUser authorizeUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        companyDetails = new CompanyDetails();
        companyDetailsResponseDTO = new CompanyDetailsResponseDTO();
        companyDetailsRequestDTO = new CompanyDetailsRequestDTO();
        companyDetailsFilled = new CompanyDetails(
                1,
                "Company Name",
                "Slogan",
                "About",
                "http://example.com",
                100,
                List.of(new City(), new City()),
                new User()
        );
    }

    @Test
    public void testFindByUserID_whenCompanyIsFound_returnCompanyDTO() {
        Mockito.when(companyDetailsRepository.findByUserId(1)).thenReturn(Optional.of(companyDetails));
        Mockito.when(companyDetailsMapper.mapToCompanyDetailsResponseDTO(companyDetails)).thenReturn(companyDetailsResponseDTO);

        CompanyDetailsResponseDTO result = companyDetailsService.findByUserID(1);
        assertEquals(companyDetailsResponseDTO, result);

        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(companyDetailsMapper, Mockito.times(1)).mapToCompanyDetailsResponseDTO(companyDetails);
    }

    @Test
    public void testFindByUserID_whenCompanyIsNotFound_throwEntityNotFoundException() {
        Mockito.when(companyDetailsRepository.findByUserId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> companyDetailsService.findByUserID(1));
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(companyDetailsMapper, Mockito.never()).mapToCompanyDetailsResponseDTO(companyDetails);
    }

    @Test
    void testUpdateByUserID_WhenCompanyIsFound_WhenAuthorizedToUpdate() {
        Mockito.when(companyDetailsRepository.findByUserId(1)).thenReturn(Optional.of(companyDetailsFilled));
        Mockito.when(companyDetailsRepository.save(companyDetailsFilled)).thenReturn(companyDetailsFilled);
        Mockito.when(companyDetailsMapper.mapToCompanyDetailsResponseDTO(companyDetailsFilled)).thenReturn(companyDetailsResponseDTO);
        Mockito.when(authorizeUser.isAuthorizedForChange(null)).thenReturn(true);

        CompanyDetailsResponseDTO result = companyDetailsService.updateByUserID(1, companyDetailsRequestDTO);

        assertEquals(result, companyDetailsResponseDTO);
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(authorizeUser, Mockito.times(1)).isAuthorizedForChange(null);
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).save(companyDetailsFilled);
        Mockito.verify(companyDetailsMapper, Mockito.times(1)).mapToCompanyDetailsResponseDTO(companyDetailsFilled);
    }

    @Test
    void testUpdateByUserID_WhenCompanyIsFound_WhenNotAuthorizedToUpdate() {
        Mockito.when(companyDetailsRepository.findByUserId(1)).thenReturn(Optional.of(companyDetailsFilled));
        Mockito.when(authorizeUser.isAuthorizedForChange(null)).thenReturn(false);

        assertThrows(UnauthorizedToUpdateException.class, () -> companyDetailsService.updateByUserID(1, companyDetailsRequestDTO));
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(authorizeUser, Mockito.times(1)).isAuthorizedForChange(null);
        Mockito.verify(companyDetailsRepository, Mockito.never()).save(companyDetailsFilled);
        Mockito.verify(companyDetailsMapper, Mockito.never()).mapToCompanyDetailsResponseDTO(companyDetailsFilled);
    }

    @Test
    public void testUpdateByUserID_whenCompanyIsNotFound_throwEntityNotFoundException() {
        Mockito.when(companyDetailsRepository.findByUserId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> companyDetailsService.updateByUserID(1, companyDetailsRequestDTO));
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(companyDetailsRepository, Mockito.never()).save(companyDetails);
        Mockito.verify(companyDetailsMapper, Mockito.never()).mapToCompanyDetailsResponseDTO(companyDetails);
    }

}