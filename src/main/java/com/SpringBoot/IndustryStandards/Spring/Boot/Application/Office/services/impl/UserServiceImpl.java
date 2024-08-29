package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.impl;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.SignUpDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.UserDTO;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.exceptions.ResourceNotFoundException;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.repositories.UserRepository;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User not Found with email "+username));
    }

    public UserEntity getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User with Id " + id + " not found"));
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserEntity Save(UserEntity newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> user = userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with this emil already exist" + signUpDTO.getEmail());
        }

        UserEntity createUser = modelMapper.map(signUpDTO, UserEntity.class);
        createUser.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        UserEntity savedUser = userRepository.save(createUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }


}
