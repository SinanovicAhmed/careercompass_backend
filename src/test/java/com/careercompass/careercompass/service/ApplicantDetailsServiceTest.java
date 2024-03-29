package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.ApplicantDetailsRequestDTO;
import com.careercompass.careercompass.dto.ApplicantDetailsResponseDTO;
import com.careercompass.careercompass.exception.UnauthorizedToUpdateException;
import com.careercompass.careercompass.mappers.ApplicantDetailsMapper;
import com.careercompass.careercompass.model.ApplicantDetails;
import com.careercompass.careercompass.model.User;
import com.careercompass.careercompass.repository.ApplicantDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ApplicantDetailsServiceTest {
    ApplicantDetails applicantDetailsFilled;
    ApplicantDetails applicantDetails;
    ApplicantDetailsResponseDTO applicantResponseDTO;
    ApplicantDetailsRequestDTO applicantRequestDTO;

    @InjectMocks
    private ApplicantDetailsService applicantDetailsService;
    @Mock
    private ApplicantDetailsRepository applicantDetailsRepository;
    @Mock
    private AuthorizeUser authorizeUser;
    @Mock
    private ApplicantDetailsMapper applicantDetailsMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        applicantDetailsFilled = new ApplicantDetails(
                1,
                "name",
                "surname",
                "+387 61 242 525",
                new User()
        );

        applicantDetails = new ApplicantDetails();
        applicantResponseDTO = new ApplicantDetailsResponseDTO();
        applicantRequestDTO = new ApplicantDetailsRequestDTO();
    }

    @Test
    public void testFindByUserID_whenApplicantIsFound_returnApplicantDTO() {
        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.of(applicantDetails));
        Mockito.when(applicantDetailsMapper.mapToApplicantDetailsResponseDTO(applicantDetails)).thenReturn(applicantResponseDTO);

        ApplicantDetailsResponseDTO result = applicantDetailsService.findByUserID(1);

        assertEquals(applicantResponseDTO, result);
        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(applicantDetailsMapper, Mockito.times(1)).mapToApplicantDetailsResponseDTO(applicantDetails);
    }

    @Test
    public void testFindByUserID_whenApplicantIsNotFound_throwEntityNotFoundException() {
        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> applicantDetailsService.findByUserID(1));

        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(applicantDetailsMapper, Mockito.never()).mapToApplicantDetailsResponseDTO(applicantDetails);
    }

    @Test
    void testUpdateByUserID_WhenApplicantIsFound_WhenAuthorizedToUpdate() {
        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.of(applicantDetailsFilled));
        Mockito.when(applicantDetailsRepository.save(applicantDetailsFilled)).thenReturn(applicantDetailsFilled);
        Mockito.when(applicantDetailsMapper.mapToApplicantDetailsResponseDTO(applicantDetailsFilled)).thenReturn(applicantResponseDTO);
        Mockito.when(authorizeUser.isAuthorizedForChange(null)).thenReturn(true);

        ApplicantDetailsResponseDTO result = applicantDetailsService.updateByUserID(1, applicantRequestDTO);

        assertEquals(applicantResponseDTO, result);

        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(authorizeUser, Mockito.times(1)).isAuthorizedForChange(null);
        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).save(applicantDetailsFilled);
        Mockito.verify(applicantDetailsMapper, Mockito.times(1)).mapToApplicantDetailsResponseDTO(applicantDetailsFilled);
    }

    @Test
    void testUpdateByUserID_WhenApplicantIsFound_WhenNotAuthorizedToUpdate() {
        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.of(applicantDetailsFilled));
        Mockito.when(authorizeUser.isAuthorizedForChange(null)).thenReturn(false);

        assertThrows(UnauthorizedToUpdateException.class, () -> applicantDetailsService.updateByUserID(1, applicantRequestDTO));

        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(authorizeUser, Mockito.times(1)).isAuthorizedForChange(null);
        Mockito.verify(applicantDetailsRepository, Mockito.never()).save(applicantDetailsFilled);
        Mockito.verify(applicantDetailsMapper, Mockito.never()).mapToApplicantDetailsResponseDTO(applicantDetailsFilled);
    }

    @Test
    void testUpdateByUserID_WhenApplicantIsNotFound_throwEntityNotFoundException() {
        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> applicantDetailsService.updateByUserID(1, applicantRequestDTO));

        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(applicantDetailsRepository, Mockito.never()).save(applicantDetails);
        Mockito.verify(applicantDetailsMapper, Mockito.never()).mapToApplicantDetailsResponseDTO(applicantDetails);
    }

}