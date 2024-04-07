package com.careercompass.careercompass.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyReviewRequestDTO {
    @NotNull(message = "applicant_id is required")
    private Integer applicant_id;

    @NotBlank(message = "positives is required")
    private String positives;

    @NotBlank(message = "negatives is required")
    private String negatives;

    @NotNull(message = "compensation_rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer compensation_rating;


    @NotNull(message = "management_rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer management_rating;

    @NotNull(message = "benefits_rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer benefits_rating;

    @NotNull(message = "communication_rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer communication_rating;

    @NotNull(message = "careergrowth_rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer careergrowth_rating;
}

