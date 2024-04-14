package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.CompanyDetailsRequestDTO;
import com.careercompass.careercompass.dto.CompanyDetailsResponseDTO;
import com.careercompass.careercompass.dto.FilteredCompanyDetailsResponseDTO;
import com.careercompass.careercompass.exception.UnauthorizedToUpdateException;
import com.careercompass.careercompass.mappers.CompanyDetailsMapper;
import com.careercompass.careercompass.model.City;
import com.careercompass.careercompass.model.CompanyDetails;
import com.careercompass.careercompass.model.CompanyReview;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
                new User(),
                List.of(new CompanyReview())
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

    @Test
    public void testFilterCompanies_whenNoFilterIsPassed_returnAllCompanies() {
        Mockito.when(companyDetailsRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(Mockito.mock(Page.class));

        companyDetailsService.filterCompanies(null, null, 0);

        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByCity(Mockito.anyInt(), Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByNameAndCity(Mockito.anyString(), Mockito.anyInt(), Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByName(Mockito.anyString(), Mockito.any(Pageable.class));
    }

    @Test
    public void testFilterCompanies_whenCompanyNameIsPassed_returnFilteredCompaniesByName() {
        Mockito.when(companyDetailsRepository.findByName(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(Mockito.mock(Page.class));

        companyDetailsService.filterCompanies("company_name", null, 0);

        Mockito.verify(companyDetailsRepository, Mockito.never()).findAll(Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByCity(Mockito.anyInt(), Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByNameAndCity(Mockito.anyString(), Mockito.anyInt(), Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByName(Mockito.anyString(), Mockito.any(Pageable.class));
    }

    @Test
    public void testFilterCompanies_whenCityIdIsPassed_returnFilteredCompaniesByCity() {

        Mockito.when(companyDetailsRepository.findByCity(Mockito.anyInt(), Mockito.any(Pageable.class)))
                .thenReturn(Mockito.mock(Page.class));

        companyDetailsService.filterCompanies(null, 1, 0);

        Mockito.verify(companyDetailsRepository, Mockito.never()).findAll(Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByCity(Mockito.anyInt(), Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByNameAndCity(Mockito.anyString(), Mockito.anyInt(), Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByName(Mockito.anyString(), Mockito.any(Pageable.class));
    }

    @Test
    public void testFilterCompanies_whenAllFiltersArePassed_returnFilteredCompanies() {
        Mockito.when(companyDetailsRepository.findByNameAndCity(Mockito.anyString(), Mockito.anyInt(), Mockito.any(Pageable.class)))
                .thenReturn(Mockito.mock(Page.class));

        companyDetailsService.filterCompanies("company_name", 1, 0);

        Mockito.verify(companyDetailsRepository, Mockito.never()).findAll(Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByCity(Mockito.anyInt(), Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findByNameAndCity(Mockito.anyString(), Mockito.anyInt(), Mockito.any(Pageable.class));
        Mockito.verify(companyDetailsRepository, Mockito.never()).findByName(Mockito.anyString(), Mockito.any(Pageable.class));
    }
}