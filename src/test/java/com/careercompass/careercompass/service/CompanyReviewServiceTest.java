package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.CompanyReviewRequestDTO;
import com.careercompass.careercompass.dto.CompanyReviewResponseDTO;
import com.careercompass.careercompass.exception.UnauthorizedToUpdateException;
import com.careercompass.careercompass.mappers.CompanyReviewMapper;
import com.careercompass.careercompass.model.ApplicantDetails;
import com.careercompass.careercompass.model.CompanyDetails;
import com.careercompass.careercompass.model.CompanyReview;
import com.careercompass.careercompass.model.User;
import com.careercompass.careercompass.repository.ApplicantDetailsRepository;
import com.careercompass.careercompass.repository.CompanyDetailsRepository;
import com.careercompass.careercompass.repository.CompanyReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CompanyReviewServiceTest {
    CompanyReview companyReview;
    CompanyReviewResponseDTO companyReviewResponseDTO;
    CompanyReviewRequestDTO companyReviewRequestDTO;
    CompanyDetails companyDetails;
    ApplicantDetails applicantDetails;
    User user;

    @InjectMocks
    private CompanyReviewService companyReviewService;

    @Mock
    private CompanyReviewRepository companyReviewRepository;
    @Mock
    private CompanyReviewMapper companyReviewMapper;
    @Mock
    private CompanyDetailsRepository companyDetailsRepository;
    @Mock
    private ApplicantDetailsRepository applicantDetailsRepository;

    @Mock
    private AuthorizeUser authorizeUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        companyReview = Mockito.mock(CompanyReview.class);
        companyReviewResponseDTO = Mockito.mock(CompanyReviewResponseDTO.class);
        companyReviewRequestDTO = Mockito.mock(CompanyReviewRequestDTO.class);
        companyDetails = Mockito.mock(CompanyDetails.class);
        applicantDetails = Mockito.mock(ApplicantDetails.class);
        user = Mockito.mock(User.class);
    }


    @Test
    public void testGetReviewsByCompanyDetailsId_whenCompanyIsFound_returnReviews() {
        List<CompanyReview> reviewList = List.of(companyReview);
        List<CompanyReviewResponseDTO> expectedResult = List.of(companyReviewResponseDTO);
        Mockito.when(companyDetailsRepository.existsById(1)).thenReturn(true);
        Mockito.when(companyReviewRepository.findByCompanyDetailsId(1)).thenReturn(reviewList);
        Mockito.when(companyReviewMapper.mapToCompanyReviewResponseDTO(companyReview)).thenReturn(companyReviewResponseDTO);

        List<CompanyReviewResponseDTO> result = companyReviewService.getReviewsByCompanyDetailsId(1);

        assertEquals(expectedResult, result);
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(companyReviewRepository, Mockito.times(1)).findByCompanyDetailsId(1);
        Mockito.verify(companyReviewMapper, Mockito.times(1)).mapToCompanyReviewResponseDTO(companyReview);
    }

    @Test
    public void testGetReviewsByCompanyDetailsId_whenCompanyIsNotFound_throwsException() {
        Mockito.when(companyDetailsRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () ->companyReviewService.getReviewsByCompanyDetailsId(1));
        Mockito.verify(companyDetailsRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(companyReviewRepository, Mockito.never()).findByCompanyDetailsId(1);
        Mockito.verify(companyReviewMapper, Mockito.never()).mapToCompanyReviewResponseDTO(companyReview);
    }

    @Test
    public void testGetReviewsByApplicantDetailsId_whenCompanyIsFound_returnReviews() {
        List<CompanyReview> reviewList = List.of(companyReview);
        List<CompanyReviewResponseDTO> expectedResult = List.of(companyReviewResponseDTO);
        Mockito.when(applicantDetailsRepository.existsById(1)).thenReturn(true);
        Mockito.when(companyReviewRepository.findByApplicantDetailsId(1)).thenReturn(reviewList);
        Mockito.when(companyReviewMapper.mapToCompanyReviewResponseDTO(companyReview)).thenReturn(companyReviewResponseDTO);

        List<CompanyReviewResponseDTO> result = companyReviewService.getReviewsByApplicantDetailsId(1);

        assertEquals(expectedResult, result);
        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(companyReviewRepository, Mockito.times(1)).findByApplicantDetailsId(1);
        Mockito.verify(companyReviewMapper, Mockito.times(1)).mapToCompanyReviewResponseDTO(companyReview);
    }

    @Test
    public void testGetReviewsByApplicantDetailsId_whenCompanyIsNotFound_throwsException() {
        Mockito.when(applicantDetailsRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () ->companyReviewService.getReviewsByApplicantDetailsId(1));
        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(companyReviewRepository, Mockito.never()).findByCompanyDetailsId(1);
        Mockito.verify(companyReviewMapper, Mockito.never()).mapToCompanyReviewResponseDTO(companyReview);
    }

    @Test
    public void testAddCompanyReview_whenCompanyAndApplicantIsFound_returnSavedReview() {
        Mockito.when(companyReviewRequestDTO.getApplicant_id()).thenReturn(1);
        Mockito.when(companyDetailsRepository.findById(1)).thenReturn(Optional.of(companyDetails));
        Mockito.when(applicantDetailsRepository.findById(1)).thenReturn(Optional.of(applicantDetails));
        Mockito.when(companyReviewRepository.save(Mockito.any(CompanyReview.class))).thenReturn(companyReview);
        Mockito.when(companyReviewMapper.mapToCompanyReviewResponseDTO(companyReview)).thenReturn(companyReviewResponseDTO);

        CompanyReviewResponseDTO result = companyReviewService.addCompanyReview(1, companyReviewRequestDTO);

        assertEquals(companyReviewResponseDTO, result);

        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findById(1);
        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findById(1);
        Mockito.verify(companyReviewRepository, Mockito.times(1)).save(Mockito.any(CompanyReview.class));
        Mockito.verify(companyReviewMapper, Mockito.times(1)).mapToCompanyReviewResponseDTO(companyReview);
    }

    @Test
    public void testAddCompanyReview_whenCompanyIsNotFound_throwException() {
        Mockito.when(companyDetailsRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(applicantDetailsRepository.findById(1)).thenReturn(Optional.of(applicantDetails));

        assertThrows(EntityNotFoundException.class, ()->companyReviewService.addCompanyReview(1, companyReviewRequestDTO));

        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findById(1);
        Mockito.verify(applicantDetailsRepository, Mockito.never()).findById(1);
        Mockito.verify(companyReviewRepository, Mockito.never()).save(companyReview);
        Mockito.verify(companyReviewMapper, Mockito.never()).mapToCompanyReviewResponseDTO(companyReview);
    }

    @Test
    public void testAddCompanyReview_whenApplicantIsNotFound_throwException() {
        Mockito.when(companyReviewRequestDTO.getApplicant_id()).thenReturn(1);
        Mockito.when(companyDetailsRepository.findById(1)).thenReturn(Optional.of(companyDetails));
        Mockito.when(applicantDetailsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->companyReviewService.addCompanyReview(1, companyReviewRequestDTO));

        Mockito.verify(companyDetailsRepository, Mockito.times(1)).findById(1);
        Mockito.verify(applicantDetailsRepository, Mockito.times(1)).findById(1);
        Mockito.verify(companyReviewRepository, Mockito.never()).save(companyReview);
        Mockito.verify(companyReviewMapper, Mockito.never()).mapToCompanyReviewResponseDTO(companyReview);
    }

    @Test
    public void deleteCompanyReview_whenReviewIsFoundAndIsAuthorized_returnConfirmationMessage() {
        Mockito.when(companyReviewRepository.findById(1)).thenReturn(Optional.of(companyReview));
        Mockito.when(companyReview.getApplicantDetails()).thenReturn(applicantDetails);
        Mockito.when(applicantDetails.getUser()).thenReturn(user);
        Mockito.when(user.getUsername()).thenReturn("username");
        Mockito.when(authorizeUser.isAuthorizedForChange("username")).thenReturn(true);
        Mockito.when(companyReviewRepository.existsById(1)).thenReturn(false);

        String result = companyReviewService.deleteCompanyReview(1);

        assertEquals("You successfully deleted review with id 1", result);

        Mockito.verify(companyReviewRepository, Mockito.times(1)).findById(1);
        Mockito.verify(companyReview, Mockito.times(1)).getApplicantDetails();
        Mockito.verify(applicantDetails, Mockito.times(1)).getUser();
        Mockito.verify(user, Mockito.times(1)).getUsername();
        Mockito.verify(authorizeUser, Mockito.times(1)).isAuthorizedForChange("username");
        Mockito.verify(companyReviewRepository, Mockito.times(1)).existsById(1);
    }

    @Test
    public void deleteCompanyReview_whenReviewIsFoundAndIsAuthorizedAndFailedToDelete_returnConfirmationMessage() {
        Mockito.when(companyReviewRepository.findById(1)).thenReturn(Optional.of(companyReview));
        Mockito.when(companyReview.getApplicantDetails()).thenReturn(applicantDetails);
        Mockito.when(applicantDetails.getUser()).thenReturn(user);
        Mockito.when(user.getUsername()).thenReturn("username");
        Mockito.when(authorizeUser.isAuthorizedForChange("username")).thenReturn(true);
        Mockito.when(companyReviewRepository.existsById(1)).thenReturn(true);

        String result = companyReviewService.deleteCompanyReview(1);

        assertEquals("Something went wrong. Review with id 1 is not deleted", result);

        Mockito.verify(companyReviewRepository, Mockito.times(1)).findById(1);
        Mockito.verify(companyReview, Mockito.times(1)).getApplicantDetails();
        Mockito.verify(applicantDetails, Mockito.times(1)).getUser();
        Mockito.verify(user, Mockito.times(1)).getUsername();
        Mockito.verify(authorizeUser, Mockito.times(1)).isAuthorizedForChange("username");
        Mockito.verify(companyReviewRepository, Mockito.times(1)).existsById(1);
    }

    @Test
    public void deleteCompanyReview_whenReviewIsFoundAndIsNotAuthorized_throwException() {
        Mockito.when(companyReviewRepository.findById(1)).thenReturn(Optional.of(companyReview));
        Mockito.when(companyReview.getApplicantDetails()).thenReturn(applicantDetails);
        Mockito.when(applicantDetails.getUser()).thenReturn(user);
        Mockito.when(user.getUsername()).thenReturn("username");
        Mockito.when(authorizeUser.isAuthorizedForChange("username")).thenReturn(false);

        assertThrows(UnauthorizedToUpdateException.class, () -> companyReviewService.deleteCompanyReview(1));

        Mockito.verify(companyReviewRepository, Mockito.times(1)).findById(1);
        Mockito.verify(companyReview, Mockito.times(1)).getApplicantDetails();
        Mockito.verify(applicantDetails, Mockito.times(1)).getUser();
        Mockito.verify(user, Mockito.times(1)).getUsername();
        Mockito.verify(authorizeUser, Mockito.times(1)).isAuthorizedForChange("username");
        Mockito.verify(companyReviewRepository, Mockito.never()).existsById(1);
    }

    @Test
    public void deleteCompanyReview_whenReviewIsNotFound_throwException() {
        Mockito.when(companyReviewRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> companyReviewService.deleteCompanyReview(1));

        Mockito.verify(companyReview, Mockito.never()).getApplicantDetails();
        Mockito.verify(applicantDetails, Mockito.never()).getUser();
        Mockito.verify(user, Mockito.never()).getUsername();
        Mockito.verify(companyReviewRepository, Mockito.times(1)).findById(1);
        Mockito.verify(authorizeUser, Mockito.never()).isAuthorizedForChange("username");
        Mockito.verify(companyReviewRepository, Mockito.never()).existsById(1);
    }
}