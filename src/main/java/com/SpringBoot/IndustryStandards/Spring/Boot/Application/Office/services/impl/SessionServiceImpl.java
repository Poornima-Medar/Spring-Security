package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.impl;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.SessionEntity;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.repositories.SessionRepository;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final int SESSION_LIMIT = 2;


    @Override
    public void generateNewSession(UserEntity user, String refreshToken) {

        List<SessionEntity> userSessions = sessionRepository.findByUser(user);

        if(userSessions.size() == SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));

            SessionEntity leastRecentlyUsedSession = userSessions.getFirst();

            sessionRepository.delete(leastRecentlyUsedSession);

        }

        SessionEntity newSession = SessionEntity.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();

        sessionRepository.save(newSession);

    }

    @Override
    public void validateRefreshToken(String refreshToken) {
        SessionEntity session = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new SessionAuthenticationException("Session not found for refreshToken: "+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());

        sessionRepository.save(session);

    }
}
