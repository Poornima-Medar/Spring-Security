package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.impl;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecreteKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecreteKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().toString())
//                .claim("roles", Set.of("ADMIN","USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*2))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L*60*60*24*30*6))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}
