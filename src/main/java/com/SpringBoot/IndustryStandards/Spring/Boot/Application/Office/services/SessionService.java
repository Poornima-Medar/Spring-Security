package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {

    public void generateNewSession(UserEntity user, String refreshToken);

    public void validateRefreshToken(String refreshToken);
}
