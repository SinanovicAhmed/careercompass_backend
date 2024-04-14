package com.careercompass.careercompass.repository;

import com.careercompass.careercompass.model.CompanyDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Integer> {
    Optional<CompanyDetails> findByUserId(Integer userId);

    @Query("SELECT cd FROM CompanyDetails cd JOIN cd.cities c WHERE cd.name = :companyName AND c.id = :cityId")
    Page<CompanyDetails> findByNameAndCity(String companyName, Integer cityId, Pageable pageable);

    Page<CompanyDetails> findByName(String companyName, Pageable pageable);

    @Query("SELECT cd FROM CompanyDetails cd JOIN cd.cities c WHERE c.id = :cityId")
    Page<CompanyDetails> findByCity(Integer cityId, Pageable pageable);
}
