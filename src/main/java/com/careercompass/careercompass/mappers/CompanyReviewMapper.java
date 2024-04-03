package com.careercompass.careercompass.mappers;

import com.careercompass.careercompass.dto.CompanyReviewResponseDTO;
import com.careercompass.careercompass.model.CompanyReview;
import org.springframework.stereotype.Component;

@Component
public class CompanyReviewMapper {
    public CompanyReviewResponseDTO mapToCompanyReviewResponseDTO(CompanyReview companyReview) {
        if(companyReview == null) {
            return null;
        }

        CompanyReviewResponseDTO companyReviewResponseDTO = new CompanyReviewResponseDTO();

        companyReviewResponseDTO.setId(companyReview.getId());
        companyReviewResponseDTO.setPositive(companyReview.getPositive());
        companyReviewResponseDTO.setNegative(companyReview.getNegative());
        companyReviewResponseDTO.setBenefits_rating(companyReview.getBenefits_rating());
        companyReviewResponseDTO.setCareergrowth_rating(companyReview.getCareergrowth_rating());
        companyReviewResponseDTO.setCompensation_rating(companyReview.getCompensation_rating());
        companyReviewResponseDTO.setManagement_rating(companyReview.getManagement_rating());
        companyReviewResponseDTO.setCommunication_rating(companyReview.getCommunication_rating());

        return companyReviewResponseDTO;
    }
}
