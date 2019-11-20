package com.app.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AppUserDto;
import com.app.service.AppUserService;

@RestController
@RequestMapping("/user")
public class AppUserController {

	
	@Autowired
	private AppUserService service;
	
	
	
	// show all employees 
	
	@GetMapping("/all")
	public ResponseEntity<List<AppUserDto>> getAll(Model map)
	{
		return new ResponseEntity<>(service.getAllAppUsers(),HttpStatus.OK);	
	}
	
}
