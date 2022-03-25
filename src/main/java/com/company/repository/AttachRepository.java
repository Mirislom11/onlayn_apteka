package com.company.repository;

import com.company.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachRepository extends JpaRepository<AttachEntity, Integer> {
    Optional<AttachEntity> findByKey(String key);
}
