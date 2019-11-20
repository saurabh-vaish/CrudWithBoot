package com.app.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.EmployeeDto;
import com.app.exception.EmployeeNotFoundException;
import com.app.service.impl.EmployeeServiceImpl;
import com.app.validator.EmployeeValidator;


@RestController
@RequestMapping("/")
public class EmployeeController {

	private static Logger log = LogManager.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeServiceImpl service;
	
	@Autowired
	private EmployeeValidator validator;
	
	
	// check username
	
	@GetMapping("/check/{userName}")
	public ResponseEntity<Object> checkUserName(@PathVariable String userName)
	{
		String msg="";

		
		  List<EmployeeDto> emp = service.getEmployeeByUserName(userName);
		  for(EmployeeDto e : emp)
		  {
			  if(e.getEmpUserName().equals(userName))
			  {
				  msg="Username already exits !!";
				  break;
			  }
		  }
		  
		log.info(msg);
		
		Map<String,String> m = new HashMap<>();
		m.put("msg",msg);
		
		return new ResponseEntity<>(m,HttpStatus.OK);
	}
	
	
	@PostMapping("save")
	public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeDto employee,Errors errors)
	{
		log.info("Entering into save method");

		String msg = "";
		
		validator.validate(employee, errors);
		
		if(!errors.hasErrors())
		{
			if(service.getEmployeeByUserName(employee.getEmpUserName()).isEmpty())
			{
				EmployeeDto emp = service.saveEmployee(employee);
			
				log.info("Employee Saved Sucessfully !!");
				
				msg = "Employee Saved Successfully With Registration Id : "+ emp.getEmpId() ;
				
				Map<String,String> m = new HashMap<>();
				m.put("msg",msg);
				
				return new ResponseEntity<>(m,HttpStatus.OK);
			}
			else {
				Map<String,String> m = new HashMap<>();
				m.put("msg","Employee username already exist ");
				
				return new ResponseEntity<>(m,HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			log.info("Employee Details Are Not Correct ");
			
			Map<String,String> m = new HashMap<>();
			m.put("msg","Employee Details Are Not Correct");
			
			return new ResponseEntity<>(m,HttpStatus.BAD_REQUEST);
		}

	}
	
	
	
	// show all employees
	
	@GetMapping("/all")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees()
	{
		log.info("getting all employees");
		return new ResponseEntity<>(service.getAllEmployees(),HttpStatus.OK);
	}
	
	
	// view one employee
	
	@GetMapping("/viewOne/{empId}")
	public ResponseEntity<EmployeeDto> viewOneEmployee(@PathVariable Integer empId)
	{
		if(service.checkEmployee(empId))
		{
			log.info("Returning Employee Details !");
			
			return new ResponseEntity<>(service.getOneEmployee(empId), HttpStatus.OK);
		}
		else
		{
			log.error("No Employee Found With Given Id");
			throw new EmployeeNotFoundException("Employee Not Found");
		}
	}
	
	
	// get employee update page
	
	@GetMapping("/update/{empId}")
	public ResponseEntity<EmployeeDto> getUpdate(@PathVariable Integer empId)
	{
		if(service.checkEmployee(empId))
		{
			log.info("Employee Found , Returing Employee Details on Update Page !");
			
			return new ResponseEntity<>(service.getOneEmployee(empId), HttpStatus.OK);
		}
		else
		{
			throw new EmployeeNotFoundException("Employee Not Found");
		}
	}
	
	
	// update employee
	
	@PostMapping("/updateEmployee")
	public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeDto employee,Errors errors)
	{
		log.info("Entering into update method");
		
		String msg = "";
		
		validator.validate(employee, errors);
		
		if(!errors.hasErrors())
		{
			Integer empId = service.updateEmployee(employee);
			
			log.info("Employee Updated Sucessfully !!");
			
			msg = "Employee Updated Successfully with Id : "+empId;
			
			Map<String,String> m = new HashMap<>();
			m.put("msg",msg);
			
			return new ResponseEntity<>(m,HttpStatus.OK);
		}
		else
		{
			log.info("Employee Details Are Not Correct ");
			
			Map<String,String> m = new HashMap<>();
			m.put("msg","Employee Details Are Not Correct");
			
			return new ResponseEntity<>(m,HttpStatus.BAD_REQUEST);
		}

	}
	
	
	// delete Employee 
	
	@DeleteMapping("/delete/{empId}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable Integer empId)
	{
		if(service.checkEmployee(empId))
		{
			service.deleteEmployee(empId);
			
			log.info("Employee Deleted Successfully  !!");
			
			Map<String,String> m = new HashMap<>();
			m.put("msg","Employee Deleted Successfully With Id : "+empId);
			
			return new ResponseEntity<>(m,HttpStatus.OK);
		}
		else
		{
			throw new EmployeeNotFoundException("Employee Not Found");
		}
	}
	
	
}
