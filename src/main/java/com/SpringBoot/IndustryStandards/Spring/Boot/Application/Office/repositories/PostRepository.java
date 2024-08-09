package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.repositories;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
