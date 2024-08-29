package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.LoginDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.SignUpDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.UserDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    UserDTO signUp(SignUpDTO signUpDTO);

    UserEntity getUserById(Long id);

    UserEntity findUserByEmail(String email);

    UserEntity Save(UserEntity newUser);
}
