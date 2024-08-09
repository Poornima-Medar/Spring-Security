package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto;


import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private String location;

    private LocalDate dateOfJoining;

    private Boolean isActive;

    private String role;

    private Double salary;
}

