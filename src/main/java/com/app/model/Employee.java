package com.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Employee {
	
	@Id
	@GeneratedValue(generator = "emp")
	@GenericGenerator(name = "emp",strategy = "increment")
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
	
	public Employee() {
		super();
	}

	public Employee(Integer empId, String empName, String empUserName, String empEmail, Long empMobile,
			String empCountry, String empState, String empCity, String empAddress, Integer empPincode) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empUserName = empUserName;
		this.empEmail = empEmail;
		this.empMobile = empMobile;
		this.empCountry = empCountry;
		this.empState = empState;
		this.empCity = empCity;
		this.empAddress = empAddress;
		this.empPincode = empPincode;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpUserName() {
		return empUserName;
	}

	public void setEmpUserName(String empUserName) {
		this.empUserName = empUserName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public Long getEmpMobile() {
		return empMobile;
	}

	public void setEmpMobile(Long empMobile) {
		this.empMobile = empMobile;
	}

	public String getEmpCountry() {
		return empCountry;
	}

	public void setEmpCountry(String empCountry) {
		this.empCountry = empCountry;
	}

	public String getEmpState() {
		return empState;
	}

	public void setEmpState(String empState) {
		this.empState = empState;
	}

	public String getEmpCity() {
		return empCity;
	}

	public void setEmpCity(String empCity) {
		this.empCity = empCity;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public Integer getEmpPincode() {
		return empPincode;
	}

	public void setEmpPincode(Integer empPincode) {
		this.empPincode = empPincode;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empUserName=" + empUserName + ", empEmail="
				+ empEmail + ", empMobile=" + empMobile + ", empCountry=" + empCountry + ", empState=" + empState
				+ ", empCity=" + empCity + ", empAddress=" + empAddress + ", empPincode=" + empPincode + "]";
	}

	
	
	

}
