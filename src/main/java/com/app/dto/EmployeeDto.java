package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
	
	private Integer empId;
	
	private String empName;
	
	private String empUserName;
	
	private String empEmail;
	
	private Long empMobile;

	private String empCountry;
	
	private String empState;
	
	private String empCity;
		
	private String empAddress;

	private Integer empPincode;
	
	private String empPassword;

	public EmployeeDto(Integer empId) {
		super();
		this.empId = empId;
	}
	
	
	
	
}
