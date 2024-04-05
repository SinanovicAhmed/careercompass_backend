package com.careercompass.careercompass.mappers;

import com.careercompass.careercompass.dto.CompanyReviewResponseDTO;
import com.careercompass.careercompass.model.ApplicantDetails;
import com.careercompass.careercompass.model.CompanyDetails;
import com.careercompass.careercompass.model.CompanyReview;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyReviewMapperTest {
    @Test
    public void testMapToCompanyReviewResponseDTO() {
        CompanyReview companyReview = new CompanyReview(
                1, "positives", "negatives", 5, 5, 5, 5, 5, new CompanyDetails(), new ApplicantDetails()
        );

        CompanyReviewResponseDTO expectedReview = new CompanyReviewResponseDTO(
                1, "positives", "negatives", 5, 5, 5, 5, 5
        );

        CompanyReviewMapper companyReviewMapper = new CompanyReviewMapper();
        assertEquals(companyReviewMapper.mapToCompanyReviewResponseDTO(companyReview), expectedReview);

    }

}