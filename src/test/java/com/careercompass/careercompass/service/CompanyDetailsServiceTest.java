package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.CompanyDetailsRequestDTO;
import com.careercompass.careercompass.dto.CompanyDetailsResponseDTO;
import com.careercompass.careercompass.mappers.CompanyDetailsMapper;
import com.careercompass.careercompass.model.CompanyDetails;
import com.careercompass.careercompass.repository.CompanyDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDetailsServiceTest {
    CompanyDetails companyDetails;
    CompanyDetailsResponseDTO companyDetailsResponseDTO;
    CompanyDetailsRequestDTO companyDetailsRequestDTO;

    @InjectMocks
    private CompanyDetailsService companyDetailsService;
    @Mock
    private CompanyDetailsRepository companyDetailsRepository;
    @Mock
    private CompanyDetailsMapper companyDetailsMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        companyDetails = new CompanyDetails();
        companyDetailsResponseDTO = new CompanyDetailsResponseDTO();
        companyDetailsRequestDTO = new CompanyDetailsRequestDTO();
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
    void testUpdateByUserID_WhenCompanyIsFound() {
        Mockito.when(companyDetailsRepository.findByUserId(1)).thenReturn(Optional.of(companyDetails));
        Mockito.when(companyDetailsRepository.save(companyDetails)).thenReturn(companyDetails);
        Mockito.when(companyDetailsMapper.mapToCompanyDetailsResponseDTO(companyDetails)).thenReturn(companyDetailsResponseDTO);

        CompanyDetailsResponseDTO result = companyDetailsService.updateByUserID(1, companyDetailsRequestDTO);

        assertEquals(result, companyDetailsResponseDTO);
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).save(companyDetails);
        Mockito.verify(companyDetailsMapper, Mockito.times(1)).mapToCompanyDetailsResponseDTO(companyDetails);
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