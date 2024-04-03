package com.careercompass.careercompass.dto;

import lombok.Data;

@Data
public class CompanyReviewResponseDTO {
    private Integer id;
    private String positive;
    private String negative;
    private Integer compensation_rating;
    private Integer management_rating;
    private Integer benefits_rating;
    private Integer communication_rating;
    private Integer careergrowth_rating;
}
