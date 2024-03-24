package com.careercompass.careercompass.mappers;

import com.careercompass.careercompass.dto.ApplicantDetailsRequestDTO;
import com.careercompass.careercompass.dto.ApplicantDetailsResponseDTO;
import com.careercompass.careercompass.model.ApplicantDetails;
import org.springframework.stereotype.Component;

@Component
public class ApplicantDetailsMapper {
    public ApplicantDetailsResponseDTO mapToApplicantDetailsResponseDTO(ApplicantDetails applicantDetails) {
        if(applicantDetails == null) {
            return null;
        }

        ApplicantDetailsResponseDTO applicantDetailsResponseDTO = new ApplicantDetailsResponseDTO();

        applicantDetailsResponseDTO.setFirstname(applicantDetails.getFirstname());
        applicantDetailsResponseDTO.setLastname(applicantDetails.getLastname());
        applicantDetailsResponseDTO.setPhoneNumber(applicantDetails.getPhoneNumber());

        return applicantDetailsResponseDTO;
    }
}
