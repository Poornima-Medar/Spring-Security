package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.controllers;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.LoginDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.LoginResponseDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.SignUpDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.UserDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.AuthService;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

private final UserService userService;

private final AuthService authService;

@Value("${deployment.type}")
private String deployment;

@PostMapping(path = "/signup")
public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
    UserDTO userDTO = userService.signUp(signUpDTO);
    return ResponseEntity.ok(userDTO);
}

@PostMapping(path = "/login")
public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO , HttpServletResponse response){
    LoginResponseDTO loginResponseDTO = authService.login(loginDTO);

    Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
    cookie.setHttpOnly(true);
    cookie.setSecure("production".equals(deployment));
    response.addCookie(cookie);

    return ResponseEntity.ok(loginResponseDTO);
}


@PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest req){
    String refreshToken = Arrays.stream(req.getCookies())
            .filter(cookie -> "refreshToken".equals(cookie.getName()))
            .findFirst()
            .map(cookie -> cookie.getValue())
            .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found"));

    LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);

    return ResponseEntity.ok(loginResponseDTO);
}

}
