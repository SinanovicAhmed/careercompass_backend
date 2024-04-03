package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.CompanyReviewRequestDTO;
import com.careercompass.careercompass.dto.CompanyReviewResponseDTO;
import com.careercompass.careercompass.exception.UnauthorizedToUpdateException;
import com.careercompass.careercompass.mappers.CompanyReviewMapper;
import com.careercompass.careercompass.model.ApplicantDetails;
import com.careercompass.careercompass.model.CompanyDetails;
import com.careercompass.careercompass.model.CompanyReview;
import com.careercompass.careercompass.repository.ApplicantDetailsRepository;
import com.careercompass.careercompass.repository.CompanyDetailsRepository;
import com.careercompass.careercompass.repository.CompanyReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyReviewService {
    private final CompanyReviewRepository companyReviewRepository;
    private final CompanyReviewMapper companyReviewMapper;
    private final CompanyDetailsRepository companyDetailsRepository;
    private final ApplicantDetailsRepository applicantDetailsRepository;
    private final AuthorizeUser authorizeUser;
    public List<CompanyReviewResponseDTO> getReviewsByCompanyDetailsId(Integer companyDetailsId) {
        if (!companyDetailsRepository.existsById(companyDetailsId)) {
            throw new EntityNotFoundException("Company with ID " + companyDetailsId + " does not exist");
        }

        List<CompanyReview> reviews = companyReviewRepository.findByCompanyDetailsId(companyDetailsId);

        List<CompanyReviewResponseDTO> responseReviews = reviews.stream()
                .map(companyReviewMapper::mapToCompanyReviewResponseDTO)
                .toList();

        return responseReviews;
    }
    public List<CompanyReviewResponseDTO> getReviewsByApplicantDetailsId(Integer applicantDetailsId) {
        if (!applicantDetailsRepository.existsById(applicantDetailsId)) {
            throw new EntityNotFoundException("Applicant with ID " + applicantDetailsId + " does not exist");
        }

        List<CompanyReview> reviews = companyReviewRepository.findByApplicantDetailsId(applicantDetailsId);

        List<CompanyReviewResponseDTO> responseReviews = reviews.stream()
                .map(companyReviewMapper::mapToCompanyReviewResponseDTO)
                .toList();

        return responseReviews;
    }


    public CompanyReviewResponseDTO addCompanyReview(Integer companyDetailsId, CompanyReviewRequestDTO review) {
        CompanyDetails companyDetails = companyDetailsRepository.findById(companyDetailsId)
                .orElseThrow(() -> new EntityNotFoundException("Details of company with ID " + companyDetailsId + " not found"));

        ApplicantDetails applicantDetails = applicantDetailsRepository.findById(review.getApplicant_id())
                .orElseThrow(() -> new EntityNotFoundException("Applicant details not found"));

        CompanyReview companyReview = setCompanyReview(review, companyDetails, applicantDetails);
        CompanyReview savedReview = companyReviewRepository.save(companyReview);

        return companyReviewMapper.mapToCompanyReviewResponseDTO(savedReview);
    }

    public String deleteCompanyReview(Integer reviewId) {
        CompanyReview companyReview = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Company review with ID " + reviewId + " not found"));

        if(!authorizeUser.isAuthorizedForChange(companyReview.getApplicantDetails().getUser().getUsername())) {
            throw new UnauthorizedToUpdateException("You don't have permission to delete review of different applicant");
        }

        companyReviewRepository.deleteById(reviewId);

        Optional<CompanyReview> deletedReviewOptional = companyReviewRepository.findById(reviewId);

        if (deletedReviewOptional.isPresent()){
            return "Something went wrong. Review with id " + reviewId + "is not deleted";
        }
        return "You successfully deleted review with id " + reviewId;
    }




    private static CompanyReview setCompanyReview(CompanyReviewRequestDTO review, CompanyDetails companyDetails, ApplicantDetails applicantDetails) {
        CompanyReview companyReview = new CompanyReview();
        companyReview.setPositive(review.getPositives());
        companyReview.setNegative(review.getNegatives());
        companyReview.setBenefits_rating(review.getBenefits_rating());
        companyReview.setCareergrowth_rating(review.getCareergrowth_rating());
        companyReview.setCompensation_rating(review.getCompensation_rating());
        companyReview.setManagement_rating(review.getManagement_rating());
        companyReview.setCommunication_rating(review.getCommunication_rating());
        companyReview.setCompanyDetails(companyDetails);
        companyReview.setApplicantDetails(applicantDetails);
        return companyReview;
    }
}
