package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.configs;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.filters.JwtAuthFilter;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Permissions.*;
import static com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Role.ADMIN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error","/auth/**","/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/post/**").authenticated()
                        /*.requestMatchers(HttpMethod.GET,"/post/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/post/**").hasRole(ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/post/**").hasAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.PUT,"/post/**").hasAnyAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/post/**").hasAnyAuthority(POST_DELETE.name())*/
                        .anyRequest().authenticated())
                .csrf(csrfConfig-> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config ->
                        oauth2Config
                                .failureUrl("/login?error=true")
                                .successHandler(oAuth2SuccessHandler)
                );
//              .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


   /* @Bean
    UserDetailsService userDetailsService(){
        UserDetails normalUser = User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();
        UserDetails adminUser = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }*/

}
