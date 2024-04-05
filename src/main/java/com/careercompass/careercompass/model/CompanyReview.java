package com.careercompass.careercompass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CompanyReview {
    @Id
    @GeneratedValue
    private Integer id;
    private String positive;
    private String negative;
    private Integer compensation_rating;
    private Integer management_rating;
    private Integer benefits_rating;
    private Integer communication_rating;
    private Integer careergrowth_rating;

    @ManyToOne
    @JoinColumn(name = "company_details_id")
    private CompanyDetails companyDetails;

    @ManyToOne
    @JoinColumn(name = "applicant_details_id")
    private ApplicantDetails applicantDetails;

    public CompanyReview(String positive, String negative, Integer compensation_rating,
                         Integer management_rating, Integer benefits_rating, Integer communication_rating,
                         Integer careergrowth_rating, CompanyDetails companyDetails, ApplicantDetails applicantDetails) {
        this.positive = positive;
        this.negative = negative;
        this.compensation_rating = compensation_rating;
        this.management_rating = management_rating;
        this.benefits_rating = benefits_rating;
        this.communication_rating = communication_rating;
        this.careergrowth_rating = careergrowth_rating;
        this.companyDetails = companyDetails;
        this.applicantDetails = applicantDetails;
    }
}
