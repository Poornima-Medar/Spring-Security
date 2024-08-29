package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.LoginDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.LoginResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponseDTO login(LoginDTO loginDTO);

    LoginResponseDTO refreshToken(String refreshToken);
}
