package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.client;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long employeeId);

    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployeeByID(Long id, EmployeeDTO employeeDTO);

    Boolean deleteEmployeeDtoById(Long employeeId);
}
