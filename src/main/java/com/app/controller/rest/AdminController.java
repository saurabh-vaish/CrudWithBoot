package com.app.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AppUserDto;
import com.app.exception.AppUserNotFoundException;
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
		Map<String,String> map = new HashMap<>();
		if(appUserDto.getUserName() !=null)
		{
			if(service.getAppUserByUserName(appUserDto.getUserName()).isEmpty())
			{
				AppUserDto dto = service.saveAppUser(appUserDto);
				
				map.put("msg", "App User with Id :"+ dto.getUserId() +"saved successfully !");
			
				return new ResponseEntity<>(map,HttpStatus.OK);
			}
			else
			{
				map.put("msg", "Duplicate User Name");
			
				return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			map.put("msg", "App User Details are not correct");
		
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	// view one employee
	
	@GetMapping("/viewOne/{userId}")
	public ResponseEntity<AppUserDto> viewOneUser(@PathVariable Integer userId)
	{
		if(service.checkAppUser(userId))
		{
			return new ResponseEntity<>(service.getOneAppUser(userId),HttpStatus.OK);
		}
		else
		{
			throw new AppUserNotFoundException("No App User Found");
		}
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<AppUserDto>> getAll()
	{
		
		List<AppUserDto> list = service.getAllAppUsers();
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	
	// update employee
	
	@PostMapping("/updateAdmin")
	public ResponseEntity<Object> updateAppUser(@RequestBody AppUserDto appUserDto)
	{
		Map<String,String> map = new HashMap<>();
		
		if(appUserDto.getUserName() !=null)
		{
			if(service.checkAppUser(appUserDto.getUserId()))
			{
				Integer id = service.updateAppUser(appUserDto);
				
				map.put("msg", "App User with Id :"+ id +" updated successfully !");
			
				return new ResponseEntity<>(map,HttpStatus.OK);
			}
			else
			{
				map.put("msg", "No AppUser Found With that Id");
			
				return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			map.put("msg", "App User Details are not correct");
		
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	// delete Employee 
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Object> deleteAppUser(@PathVariable Integer userId)
	{
		Map<String,String> map = new HashMap<>();
		
		if(service.checkAppUser(userId))
		{
			service.deleteAppUser(userId);
			
			map.put("msg", "user Deleted Successfully ");
			
			return new ResponseEntity<>(map,HttpStatus.OK);
						
		}
		else
		{
			throw new AppUserNotFoundException("No App User Found");
		}
	}
	
	
}
