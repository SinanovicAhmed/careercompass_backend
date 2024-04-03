package com.careercompass.careercompass.controller;

import com.careercompass.careercompass.dto.CompanyReviewRequestDTO;
import com.careercompass.careercompass.dto.CompanyReviewResponseDTO;
import com.careercompass.careercompass.service.CompanyReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/company/reviews")
public class CompanyReviewController {
    private final CompanyReviewService companyReviewService;

    @GetMapping("/company/{companyDetailsId}")
    public ResponseEntity<List<CompanyReviewResponseDTO>> getAllReviewsByCompany(@PathVariable Integer companyDetailsId) {
        return new ResponseEntity<>(companyReviewService.getReviewsByCompanyDetailsId(companyDetailsId), HttpStatus.OK);
    }

    @GetMapping("/applicant/{applicantDetailsId}")
    public ResponseEntity<List<CompanyReviewResponseDTO>> getAllReviewsByApplicant(@PathVariable Integer applicantDetailsId) {
        return new ResponseEntity<>(companyReviewService.getReviewsByApplicantDetailsId(applicantDetailsId), HttpStatus.OK);
    }

    @PostMapping("/add/{companyDetailsId}")
    public ResponseEntity<CompanyReviewResponseDTO> addCompanyReview
            (@PathVariable Integer companyDetailsId, @Valid @RequestBody CompanyReviewRequestDTO review) {
        return new ResponseEntity<>(companyReviewService.addCompanyReview(companyDetailsId, review), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteCompanyReview
            (@PathVariable Integer reviewId) {
        return new ResponseEntity<>(companyReviewService.deleteCompanyReview(reviewId), HttpStatus.OK);
    }
}
