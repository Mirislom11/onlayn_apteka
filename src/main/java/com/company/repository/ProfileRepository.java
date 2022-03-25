package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByLoginAndPassword(String login, String password);
    List<ProfileEntity> findAllByRole(ProfileRole role);
    @Transactional
    @Modifying
    void deleteByLogin(String login);
    Optional<ProfileEntity> findByLogin(String login);
}
