package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.repositories;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
