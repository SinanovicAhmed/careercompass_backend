package com.careercompass.careercompass.repository;

import com.careercompass.careercompass.model.ApplicantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ApplicantDetailsRepository extends JpaRepository<ApplicantDetails, Integer> {
    Optional<ApplicantDetails> findByUserId(Integer userId);
}
