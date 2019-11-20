package com.app.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AppUserDto;
import com.app.dto.EmployeeDto;
import com.app.exception.EmployeeNotFoundException;
import com.app.service.AppUserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AppUserService service;

	
	@GetMapping("/check/{userName}")
	public ResponseEntity<Object> checkUserName(@PathVariable String userName)
	{
		String msg="";

		
		  List<AppUserDto> user = service.getAppUserByUserName(userName);
		  for(AppUserDto e : user)
		  {
			  if(e.getUserName().equals(userName))
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
	
	
	@PostMapping("/register")
	public ResponseEntity<Object> regsiterUser(@RequestBody AppUserDto appUserDto)
	{
		if(appUserDto.getUserName() !=null)
		{
			if(service.getAppUserByUserName(appUserDto.getUserName()).isEmpty())
			{
				AppUserDto dto = service.saveAppUser(appUserDto);
				
				Map<String,String> map = new HashMap<>();
				map.put("msg", "App User with Id :"+ dto.getUserId() +"saved successfully !");
			
				return new ResponseEntity<>(map,HttpStatus.OK);
			}
			else
			{
				Map<String,String> map = new HashMap<>();
				map.put("msg", "Duplicate User Name");
			
				return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			Map<String,String> map = new HashMap<>();
			map.put("msg", "App User Details are not correct");
		
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	// view one employee
	
	@GetMapping("/viewOne/{userId}")
	public ResponseEntity<AppUserDto> viewOneEmployee(@PathVariable Integer userId)
	{
		if(service.checkAppUser(userId))
		{
			return new ResponseEntity<>(service.getOneAppUser(userId),HttpStatus.OK);
		}
		else
		{
			throw new EmployeeNotFoundException("No Employee Found");
		}
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<AppUserDto>> getAll()
	{
		
		List<AppUserDto> list = service.getAllAppUsers();
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	
	// update employee
	
	@PostMapping("/update")
	public ResponseEntity<String> updateEmployee(@RequestBody EmployeeDto employeeDto)
	{
		if(employeeDto.getEmpEmail() !=null)
		{
			
//				EmployeeDto dto = service.updateEmployee(employeeDto);
//				return new ResponseEntity<String>("Employee with Id :"+ dto.getEmpId() +" Upadted successfully !",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Employee Details are not correct",HttpStatus.BAD_REQUEST);
		}
		return null;
		
	}
	
	
	// delete Employee 
	
	@GetMapping("/delete/{empId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Integer empId)
	{
//		if(service.checkEmployee(empId))
//		{
//			service.deleteEmployee(empId);
//			
//			return new ResponseEntity<String>("Employee Deleted SuccessFully with Given Id :"+empId,HttpStatus.OK);
//						
//		}
//		else
//		{
//			throw new EmployeeNotFoundException();
//		}
		return null;
	}
	
	
}
