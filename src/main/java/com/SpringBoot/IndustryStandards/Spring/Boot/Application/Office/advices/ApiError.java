package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime localDateTime;

    private String message;

    private HttpStatus httpStatus;

    public ApiError() {
        this.localDateTime = LocalDateTime.now();
    }

    public ApiError(String message, HttpStatus httpStatus) {
        this();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
