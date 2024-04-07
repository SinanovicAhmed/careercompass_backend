package com.careercompass.careercompass.repository;

import com.careercompass.careercompass.model.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Integer> {
    List<CompanyReview> findByCompanyDetailsId(Integer companyDetailsId);
    List<CompanyReview> findByApplicantDetailsId(Integer companyDetailsId);
}
