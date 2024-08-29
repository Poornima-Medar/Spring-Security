package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String generateAccessToken(UserEntity user);

    String generateRefreshToken(UserEntity user);

    Long getUserIdFromToken(String Token);

}
