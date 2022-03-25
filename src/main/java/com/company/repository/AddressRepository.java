package com.company.repository;

import com.company.entity.AddressEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> , JpaSpecificationExecutor<AddressEntity> {
    @Query("SELECT a FROM AddressEntity a WHERE a.profile = ?1")
    Optional<AddressEntity> findAddressEntityByProfile(ProfileEntity profile);

    @Override
    void deleteById(Integer integer);

    List<AddressEntity> findAllByCountry(String country);
}
