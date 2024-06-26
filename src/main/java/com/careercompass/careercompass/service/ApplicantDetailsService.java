package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.ApplicantDetailsRequestDTO;
import com.careercompass.careercompass.dto.ApplicantDetailsResponseDTO;
import com.careercompass.careercompass.exception.UnauthorizedToUpdateException;
import com.careercompass.careercompass.mappers.ApplicantDetailsMapper;
import com.careercompass.careercompass.model.ApplicantDetails;
import com.careercompass.careercompass.repository.ApplicantDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicantDetailsService {
    private final ApplicantDetailsRepository applicantDetailsRepository;
    private final ApplicantDetailsMapper applicantDetailsMapper;
    private final AuthorizeUser authorizeUser;

    public ApplicantDetailsResponseDTO findByUserID(Integer id) {
        ApplicantDetails applicantDetails = applicantDetailsRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Details of applicant with ID " + id + " not found"));

        return applicantDetailsMapper.mapToApplicantDetailsResponseDTO(applicantDetails);
    }

    public ApplicantDetailsResponseDTO updateByUserID(Integer id, ApplicantDetailsRequestDTO newApplicantDetails) {
        ApplicantDetails applicantDetails = applicantDetailsRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Details of applicant with ID " + id + " not found"));

        if(!authorizeUser.isAuthorizedForChange(applicantDetails.getUser().getUsername())) {
            throw new UnauthorizedToUpdateException("You don't have permission to do update details of this user");
        }

        applicantDetails.setFirstname(newApplicantDetails.getFirstname());
        applicantDetails.setLastname(newApplicantDetails.getLastname());
        applicantDetails.setPhoneNumber(newApplicantDetails.getPhoneNumber());

        ApplicantDetails updatedApplicantDetails = applicantDetailsRepository.save(applicantDetails);
        return applicantDetailsMapper.mapToApplicantDetailsResponseDTO(updatedApplicantDetails);
    }
}
