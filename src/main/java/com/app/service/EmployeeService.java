package com.app.service;

import java.util.List;

import com.app.dto.EmployeeDto;

public interface EmployeeService {

	public EmployeeDto saveEmployee(EmployeeDto emp);
	
	public Integer updateEmployee(EmployeeDto emp);
	
	public EmployeeDto getOneEmployee(Integer empId);
	
	public List<EmployeeDto> getAllEmployees();
	
	public void deleteEmployee(Integer empId);
	
	public Boolean checkEmployee(Integer empId);
	
	public List<EmployeeDto> getEmployeeByUserName(String username);

	public Integer countUnApproved();
	 
	
}
