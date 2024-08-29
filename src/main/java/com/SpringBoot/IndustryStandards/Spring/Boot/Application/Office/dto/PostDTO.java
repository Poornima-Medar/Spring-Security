package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto;


import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;

    private String title;

    private String description;

    private UserDTO author;
}
