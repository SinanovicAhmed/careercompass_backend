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

        CompanyReviewResponseDTO companyReviewResponseDTO = new CompanyReviewResponseDTO(
                companyReview.getId(),
                companyReview.getPositive(),
                companyReview.getNegative(),
                companyReview.getBenefits_rating(),
                companyReview.getCareergrowth_rating(),
                companyReview.getCompensation_rating(),
                companyReview.getManagement_rating(),
                companyReview.getCommunication_rating()
        );

        return companyReviewResponseDTO;
    }
}
