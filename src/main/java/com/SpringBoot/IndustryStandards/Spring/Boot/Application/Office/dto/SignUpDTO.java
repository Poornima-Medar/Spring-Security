package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Permissions;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permissions> permissions;
}
