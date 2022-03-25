package com.company.repository;

import com.company.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Integer> {
    Optional<PrescriptionEntity> findByPrescriptionName(String prescriptionName);
}
