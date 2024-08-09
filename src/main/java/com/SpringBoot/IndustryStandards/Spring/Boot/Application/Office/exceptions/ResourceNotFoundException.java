package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
