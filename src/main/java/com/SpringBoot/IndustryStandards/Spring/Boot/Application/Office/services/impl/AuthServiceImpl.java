package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.impl;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.LoginDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.LoginResponseDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.AuthService;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.JwtService;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.SessionService;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;

    private final SessionService sessionService;

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        sessionService.generateNewSession(user, refreshToken);

        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);

    }

    @Override
    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);

       sessionService.validateRefreshToken(refreshToken);
        UserEntity user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);


    }
}
