package com.careercompass.careercompass.controller;

import com.careercompass.careercompass.dto.ApplicantDetailsRequestDTO;
import com.careercompass.careercompass.dto.ApplicantDetailsResponseDTO;
import com.careercompass.careercompass.service.ApplicantDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applicant/details")
@RequiredArgsConstructor
public class ApplicantDetailsController {
    private final ApplicantDetailsService applicantDetailsService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ApplicantDetailsResponseDTO> getApplicantDetails(@PathVariable Integer id) {
        return new ResponseEntity<>(applicantDetailsService.findByUserID(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicantDetailsResponseDTO> updateApplicantDetails(
            @PathVariable Integer id, @Valid @RequestBody ApplicantDetailsRequestDTO newApplicantDetails
    ) {
        return new ResponseEntity<>(applicantDetailsService.updateByUserID(id, newApplicantDetails), HttpStatus.OK);
    }
}
