package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.ApplicantDetailsRequestDTO;
import com.careercompass.careercompass.dto.ApplicantDetailsResponseDTO;
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
    @InjectMocks
    private ApplicantDetailsService applicantDetailsService;
    @Mock
    private ApplicantDetailsRepository applicantDetailsRepository;
    @Mock
    private ApplicantDetailsMapper applicantDetailsMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUserID_whenApplicantIsFound_returnApplicantDTO() {
        ApplicantDetails applicantDetails = new ApplicantDetails(
                2,
                "Ahmed",
                "Sinanovic",
                "+387 61 242 525",
                new User()
        );

        ApplicantDetailsResponseDTO expectedResponseDTO = new ApplicantDetailsResponseDTO(
                "Ahmed",
                "Sinanovic",
                "+387 61 242 525"
        );

        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.of(applicantDetails));
        Mockito.when(applicantDetailsMapper.mapToApplicantDetailsResponseDTO(applicantDetails)).thenReturn(expectedResponseDTO);

        ApplicantDetailsResponseDTO responseDTO = applicantDetailsService.findByUserID(1);

        assertEquals(expectedResponseDTO, responseDTO);
        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(applicantDetailsMapper, Mockito.times(1)).mapToApplicantDetailsResponseDTO(applicantDetails);
    }

    @Test
    public void testFindByUserID_whenApplicantIsNotFound_throwEntityNotFoundException() {
        ApplicantDetails applicantDetails = new ApplicantDetails();

        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> applicantDetailsService.findByUserID(1));

        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(applicantDetailsMapper, Mockito.never()).mapToApplicantDetailsResponseDTO(applicantDetails);
    }

    @Test
    void testUpdateByUserID_WhenApplicantIsFound() {
        // Arrange
        ApplicantDetailsRequestDTO newApplicantDetails = new ApplicantDetailsRequestDTO(
                "NewFirstName",
                "NewLastName",
                "+1234567890"
        );

        ApplicantDetailsResponseDTO returnedApplicantDetails = new ApplicantDetailsResponseDTO(
                "NewFirstName",
                "NewLastName",
                "+1234567890"
        );

        ApplicantDetails applicantDetails = new ApplicantDetails();
        ApplicantDetails updatedApplicantDetails = new ApplicantDetails();

        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.of(applicantDetails));
        Mockito.when(applicantDetailsRepository.save(applicantDetails)).thenReturn(updatedApplicantDetails);
        Mockito.when(applicantDetailsMapper.mapToApplicantDetailsResponseDTO(updatedApplicantDetails)).thenReturn(returnedApplicantDetails);

        ApplicantDetailsResponseDTO responseDTO = applicantDetailsService.updateByUserID(1, newApplicantDetails);

        assertEquals(returnedApplicantDetails, responseDTO);

        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).save(applicantDetails);
        Mockito.verify(applicantDetailsMapper, Mockito.times(1)).mapToApplicantDetailsResponseDTO(updatedApplicantDetails);
    }

    @Test
    void testUpdateByUserID_WhenApplicantIsNotFound_throwEntityNotFoundException() {
        ApplicantDetailsRequestDTO newApplicantDetails = new ApplicantDetailsRequestDTO(
                "NewFirstName",
                "NewLastName",
                "+1234567890"
        );
        ApplicantDetails applicantDetails = new ApplicantDetails();
        ApplicantDetails updatedApplicantDetails = new ApplicantDetails();

        Mockito.when(applicantDetailsRepository.findByUserId(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> applicantDetailsService.updateByUserID(1, newApplicantDetails));

        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findByUserId(1);
        Mockito.verify(applicantDetailsRepository, Mockito.never()).save(applicantDetails);
        Mockito.verify(applicantDetailsMapper, Mockito.never()).mapToApplicantDetailsResponseDTO(updatedApplicantDetails);
    }

}