package com.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.EmployeeDto;
import com.app.exception.EmployeeNotFoundException;
import com.app.model.Employee;
import com.app.repo.EmployeeRepository;
import com.app.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;


	@Override
	@Transactional
	public EmployeeDto saveEmployee(EmployeeDto emp) {

		Employee e = new Employee();
		e.setEmpId(emp.getEmpId());
		e.setEmpAddress(emp.getEmpAddress());
		e.setEmpCity(emp.getEmpCity());
		e.setEmpCountry(emp.getEmpCountry());
		e.setEmpEmail(emp.getEmpEmail());
		e.setEmpMobile(emp.getEmpMobile());
		e.setEmpName(emp.getEmpName());
		e.setEmpPincode(emp.getEmpPincode());
		e.setEmpState(emp.getEmpState());
		e.setEmpUserName(emp.getEmpUserName());

		Employee savedEmpl = repo.save(e);

		emp.setEmpId(savedEmpl.getEmpId());

		return emp;
	}

	@Override
	@Transactional
	public Integer updateEmployee(EmployeeDto emp) {

		Employee e = new Employee();
		e.setEmpId(emp.getEmpId());
		e.setEmpAddress(emp.getEmpAddress());
		e.setEmpCity(emp.getEmpCity());
		e.setEmpCountry(emp.getEmpCountry());
		e.setEmpEmail(emp.getEmpEmail());
		e.setEmpMobile(emp.getEmpMobile());
		e.setEmpName(emp.getEmpName());
		e.setEmpPincode(emp.getEmpPincode());
		e.setEmpState(emp.getEmpState());
		e.setEmpUserName(emp.getEmpUserName());

		Employee savedEmpl = repo.save(e);

		emp.setEmpId(savedEmpl.getEmpId());

		return emp.getEmpId();
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeeDto getOneEmployee(Integer empId) {

		Optional<Employee> emp=repo.findById(empId);

		if(emp.isPresent()) {
			Employee e = emp.get();
			
			EmployeeDto employee = new EmployeeDto();
			employee.setEmpId(e.getEmpId());
			employee.setEmpAddress(e.getEmpAddress());
			employee.setEmpCity(e.getEmpCity());
			employee.setEmpCountry(e.getEmpCountry());
			employee.setEmpEmail(e.getEmpEmail());
			employee.setEmpMobile(e.getEmpMobile());
			employee.setEmpName(e.getEmpName());
			employee.setEmpPincode(e.getEmpPincode());
			employee.setEmpState(e.getEmpState());
			employee.setEmpUserName(e.getEmpUserName());

			return employee;
		}
		else throw new EmployeeNotFoundException("EmployeeDto Not Found");

	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeDto> getAllEmployees() {
		return repo.findAll(Sort.by(Direction.ASC,"empId")).parallelStream().map(e-> {
			
			EmployeeDto employee = new EmployeeDto();
			employee.setEmpId(e.getEmpId());
			employee.setEmpAddress(e.getEmpAddress());
			employee.setEmpCity(e.getEmpCity());
			employee.setEmpCountry(e.getEmpCountry());
			employee.setEmpEmail(e.getEmpEmail());
			employee.setEmpMobile(e.getEmpMobile());
			employee.setEmpName(e.getEmpName());
			employee.setEmpPincode(e.getEmpPincode());
			employee.setEmpState(e.getEmpState());
			employee.setEmpUserName(e.getEmpUserName());
			
			return employee;
		}).collect(Collectors.toList());
	}

	
	@Override
	@Transactional
	public void deleteEmployee(Integer empId) {
		repo.deleteById(empId);
	}

	
	@Override
	public Boolean checkEmployee(Integer empId) {
		return repo.existsById(empId);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<EmployeeDto> getEmployeeByUserName(String username) {
		return repo.findByEmpUserName(username).parallelStream().map(e->{
			
			EmployeeDto employee = new EmployeeDto();
			employee.setEmpId(e.getEmpId());
			employee.setEmpAddress(e.getEmpAddress());
			employee.setEmpCity(e.getEmpCity());
			employee.setEmpCountry(e.getEmpCountry());
			employee.setEmpEmail(e.getEmpEmail());
			employee.setEmpMobile(e.getEmpMobile());
			employee.setEmpName(e.getEmpName());
			employee.setEmpPincode(e.getEmpPincode());
			employee.setEmpState(e.getEmpState());
			employee.setEmpUserName(e.getEmpUserName());
			
			return employee;
		}).collect(Collectors.toList());
	}


}
