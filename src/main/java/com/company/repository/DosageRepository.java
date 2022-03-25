package com.company.repository;

import com.company.entity.DosageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DosageRepository extends JpaRepository<DosageEntity, Integer> {
    Optional<DosageEntity> findByDosageName(String dosageName);
}
