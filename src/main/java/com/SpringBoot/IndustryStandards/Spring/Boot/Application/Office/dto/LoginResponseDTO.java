package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private Long id;

    private String accessToken;

    private String refreshToken;

}
