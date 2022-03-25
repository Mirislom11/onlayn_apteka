package com.company.repository;

import com.company.entity.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreatmentRepository extends JpaRepository<TreatmentEntity, Integer> {

    Optional<TreatmentEntity> findByDisease(String disease);
}
