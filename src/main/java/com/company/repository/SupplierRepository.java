package com.company.repository;

import com.company.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer>{
    Optional<SupplierEntity> findSupplierEntityByCompanyName(String companyName);
    @Override
    void deleteById(Integer integer);
}
