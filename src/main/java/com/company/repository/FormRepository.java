package com.company.repository;

import com.company.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Integer> {
    Optional<FormEntity> findByFormName(String name);
}
