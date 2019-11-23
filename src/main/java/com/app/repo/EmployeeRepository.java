package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	List<Employee> findByEmpUserName(String empUserName);

	List<Employee> findByApproved(Boolean approved);
	
}
