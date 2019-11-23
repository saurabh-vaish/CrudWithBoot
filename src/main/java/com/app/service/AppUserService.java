package com.app.service;

import java.util.List;

import com.app.dto.AppUserDto;
import com.app.dto.EmployeeDto;

public interface AppUserService {

	public AppUserDto saveAppUser(AppUserDto user);
	
	public Integer updateAppUser(AppUserDto user);
	
	public AppUserDto getOneAppUser(Integer userId);
	
	public List<AppUserDto> getAllAppUsers();
	
	public void deleteAppUser(Integer userId);
	
	public Boolean checkAppUser(Integer userId);
	
	public List<AppUserDto> getAppUserByUserName(String username);

	public List<EmployeeDto> getAllUnApprovedEmployee();

	public Integer approveEmployeeRequest(Integer empId);

	 
	
}
