package com.careercompass.careercompass.mappers;

import com.careercompass.careercompass.dto.ApplicantDetailsResponseDTO;
import com.careercompass.careercompass.model.ApplicantDetails;
import com.careercompass.careercompass.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicantDetailsMapperTest {
    @Test
    public void testMapToApplicantDetailsResponseDTO() {
        ApplicantDetails applicantDetails = new ApplicantDetails(
                2,
                "Ahmed",
                "Sinanovic",
                "+387 64 252 522",
                new User()
        );
        ApplicantDetailsResponseDTO expectedResponseDTO = new ApplicantDetailsResponseDTO(
                "Ahmed",
                "Sinanovic",
                "+387 64 252 522"
        );

        ApplicantDetailsMapper mapper = new ApplicantDetailsMapper();
        ApplicantDetailsResponseDTO responseDTO = mapper.mapToApplicantDetailsResponseDTO(applicantDetails);

        assertEquals(expectedResponseDTO, responseDTO);
    }

    @Test
    public void testMapToApplicantDetailsResponseDTO_NullInput() {
        ApplicantDetailsMapper mapper = new ApplicantDetailsMapper();

        assertNull(mapper.mapToApplicantDetailsResponseDTO(null));
    }
}