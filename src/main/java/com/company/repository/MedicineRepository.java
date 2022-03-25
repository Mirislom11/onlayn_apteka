package com.company.repository;

import com.company.controller.MedicineController;
import com.company.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, Integer> , JpaSpecificationExecutor<MedicineEntity> {
    Optional<MedicineEntity> findByMedicineNameLike(String medicineName);

}
