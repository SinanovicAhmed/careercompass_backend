package com.careercompass.careercompass.controller;

import com.careercompass.careercompass.dto.CompanyDetailsRequestDTO;
import com.careercompass.careercompass.dto.CompanyDetailsResponseDTO;
import com.careercompass.careercompass.service.CompanyDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyDetailsController {
    private final CompanyDetailsService companyDetailsService;


    @GetMapping("/get/{id}")
    public ResponseEntity<CompanyDetailsResponseDTO> getCompanyDetails(@PathVariable Integer id) {
        return new ResponseEntity<>(companyDetailsService.findByUserID(id), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyDetailsResponseDTO> updateCompanyDetails(
            @PathVariable Integer id, @Valid @RequestBody CompanyDetailsRequestDTO newCompanyDetails
    ) {
        return new ResponseEntity<>(companyDetailsService.updateByUserID(id, newCompanyDetails), HttpStatus.OK);
    }
}
