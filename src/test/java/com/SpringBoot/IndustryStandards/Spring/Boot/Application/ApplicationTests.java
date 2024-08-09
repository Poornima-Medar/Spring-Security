package com.SpringBoot.IndustryStandards.Spring.Boot.Application;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.client.EmployeeClient;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.dto.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Test
	@Order(5)
	void getAllEmployeeTest(){
		List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
		System.out.println(employeeDTOList);
	}

	@Test
	@Order(2)
	void getEmployeeByIdTest(){
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(1L);
		System.out.println(employeeDTO);
	}

	@Test
	@Order(1)
	void createNewEmployeeTest(){
		EmployeeDTO employeeDTO = new EmployeeDTO(null,"Poornima",20,"poornima@gmail.com",
				"Gadag", LocalDate.of(2000,2,12),true,"ADMIN", 2000.0);
		EmployeeDTO savedEmployee = employeeClient.createNewEmployee(employeeDTO);
		System.out.println(savedEmployee);
	}

	@Test
	@Order(3)
	void updateEmployeeByIdTest(){
		EmployeeDTO employeeDTO = new EmployeeDTO(1L,"abcd",21,"abc@gmail.com",
				"Hubli", LocalDate.of(2020,2,12),true,"USER", 2012.0);
		EmployeeDTO savedEmployee = employeeClient.updateEmployeeByID(1L, employeeDTO);
		System.out.println(savedEmployee);
	}

	@Test
	@Order(4)
	void deleteEmployeeByIdTest(){
		boolean employeeDTO = employeeClient.deleteEmployeeDtoById(3L);
		System.out.println(employeeDTO);
	}


}
