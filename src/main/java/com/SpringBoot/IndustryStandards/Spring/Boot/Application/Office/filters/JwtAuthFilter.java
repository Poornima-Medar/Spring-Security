package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.filters;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.JwtService;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            log.debug("token not found");
            return;
        }

        log.info("Splitting the token");
        String token = requestTokenHeader.split("Bearer ")[1];

        log.trace("generating userId from token");
        Long userId = jwtService.getUserIdFromToken(token);
        log.debug("user Id is {}",userId);


        log.debug("Starting adding authentication inside the security context");
        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            log.trace("getting user using getUserById");
            UserEntity user = userService.getUserById(userId);
            log.debug("found user Details {}", user);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);

//        with the response we log the information

    }
}
