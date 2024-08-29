package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.handler;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.JwtService;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    private final JwtService jwtService;

    @Value("${deployment.type}")
    private String deployment;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        log.info(oAuth2User.getAttribute("email"));

        String email = oAuth2User.getAttribute("email");

        UserEntity user = userService.findUserByEmail(email);

        if(user == null){
            UserEntity newUser =  UserEntity.builder()
                                    .name(oAuth2User.getAttribute("name"))
                                    .email(email)
                                    .build();
            user = userService.Save(newUser);
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployment));
        response.addCookie(cookie);

        String fontEndUrl = "http://localhost:9000/home.html?token="+accessToken;

        getRedirectStrategy().sendRedirect(request,response,fontEndUrl);

//        response.sendRedirect(fontEndUrl);

    }

}
