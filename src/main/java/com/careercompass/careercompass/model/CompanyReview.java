package com.careercompass.careercompass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
