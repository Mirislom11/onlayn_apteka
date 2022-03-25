package com.company.repository;

import com.company.entity.IncomeMedicineEntity;
import com.company.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface IncomeMedicineRepository extends JpaRepository<IncomeMedicineEntity, Integer> {
    @Transactional
    @Modifying
    void deleteByMedicine(MedicineEntity medicine);
}
