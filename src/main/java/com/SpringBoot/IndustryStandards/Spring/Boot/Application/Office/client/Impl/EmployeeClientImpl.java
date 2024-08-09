package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.client.Impl;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.advices.ApiResponse;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.client.EmployeeClient;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        /*log.error("error log");
        log.warn("Warn log");
        log.info("info log");
        log.debug("debug log");
        log.trace("trace log")*/;
       log.trace("trying to get all employees data using getAllEmployee");
       try {
           ApiResponse<List<EmployeeDTO>> employeeDTOList =  restClient.get()
                   .uri("/employee")
                   .retrieve()
                   .body(new ParameterizedTypeReference<>() {
                   });
           log.debug("Successfully retrieved all employees");
           log.trace("Retrieved all employees data using getAllEmployee: {}", employeeDTOList.getData());
           log.info("End of Method");
           return employeeDTOList.getData();
       }
       catch (Exception e){
           log.error("Error in getting all employees",e);
           throw new RuntimeException(e);
       }

    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {

       ApiResponse<EmployeeDTO> employeeDTO = restClient.get()
                .uri("/employee/{employeeId}", employeeId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

       return employeeDTO.getData();

    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        log.debug("creating new employee using createNewEmployee Method");
        ResponseEntity<ApiResponse<EmployeeDTO>> employee = restClient.post()
                .uri("/employee")
                .body(employeeDTO)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req,res) -> {
                    log.error(new String(res.getBody().readAllBytes()));
                })
                .toEntity(new ParameterizedTypeReference<>() {
                });
        log.debug("Successfully inserted new employee data");
        log.trace("inserted new employee data createNewEmployee : {}", employee.getBody().getData());
        return employee.getBody().getData();
    }

    @Override
    public EmployeeDTO updateEmployeeByID(Long employeeId, EmployeeDTO employeeDTO) {
        ApiResponse<EmployeeDTO> updateEmployeeDTO = restClient.put()
                .uri("/employee/{employeeId}", employeeId)
                .body(employeeDTO)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return updateEmployeeDTO.getData();
    }

    @Override
    public Boolean deleteEmployeeDtoById(Long employeeId) {
        ApiResponse<Boolean> deleteEmployee = restClient.delete()
                .uri("/employee/{employeeId}", employeeId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return deleteEmployee.getData();
    }
}
