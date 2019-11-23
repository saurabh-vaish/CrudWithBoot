package com.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.AppUserDto;
import com.app.dto.EmployeeDto;
import com.app.model.AppUser;
import com.app.model.Employee;
import com.app.repo.AppUserRepository;
import com.app.repo.EmployeeRepository;
import com.app.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository repo;
	
	@Autowired
	private EmployeeRepository erepo;

	@Autowired
	private SCryptPasswordEncoder enc;

	@Override
	@Transactional
	public AppUserDto saveAppUser(AppUserDto user) {

		AppUser app = new AppUser();
		app.setUserId(user.getUserId());
		app.setUserName(user.getUserName());
		app.setUserEmail(user.getUserEmail());
		app.setUserPassword(enc.encode(user.getUserPassword()));
		app.setUserRoles(user.getUserRoles());

		AppUser savedUser = repo.save(app);

		user.setUserId(savedUser.getUserId());
		user.setUserId(1);

		return user;
	}

	@Override
	@Transactional
	public Integer updateAppUser(AppUserDto user) {

		Optional<AppUser> appuser =  repo.findById(user.getUserId());
		AppUser app = new AppUser();

		if(appuser.isPresent())
		{
			app = appuser.get();
		}

		app.setUserName(user.getUserName());
		app.setUserEmail(user.getUserEmail());
		app.setUserRoles(user.getUserRoles());

		repo.save(app);

		return user.getUserId();
	}


	@Override
	@Transactional(readOnly = true)
	public List<AppUserDto> getAllAppUsers() {

		return repo.findAll(Sort.by(Direction.ASC,"userId")).parallelStream().map(user-> {

			AppUserDto app = new AppUserDto();
			app.setUserId(user.getUserId());
			app.setUserEmail(user.getUserEmail());
			app.setUserName(user.getUserName());
			app.setUserPassword(null);
			app.setUserRoles(user.getUserRoles());

			return app;

		}).collect(Collectors.toList());
	}


	@Override
	@Transactional
	public void deleteAppUser(Integer empId) {
		repo.deleteById(empId);
	}


	@Override
	public Boolean checkAppUser(Integer empId) {
		return repo.existsById(empId);
	}


	@Override
	@Transactional(readOnly = true)
	public List<AppUserDto> getAppUserByUserName(String username) {
		return repo.findByUserName(username).parallelStream().map(user->{

			AppUserDto app = new AppUserDto();
			app.setUserId(user.getUserId());
			app.setUserName(user.getUserName());
			app.setUserEmail(user.getUserEmail());
			app.setUserPassword(user.getUserPassword());
			app.setUserRoles(user.getUserRoles());

			return app;
		}).collect(Collectors.toList());
	}

	@Override
	public AppUserDto getOneAppUser(Integer userId) {

		Optional<AppUser> app =  repo.findById(userId);
		AppUserDto dto = null;
		if(app.isPresent())
		{
			AppUser user = app.get();

			dto = new AppUserDto();
			dto.setUserId(user.getUserId());
			dto.setUserName(user.getUserName());
			dto.setUserEmail(user.getUserEmail());
			dto.setUserPassword(null);
			dto.setUserRoles(user.getUserRoles());

		}
		return dto;
	}

	@Override
	public List<EmployeeDto> getAllUnApprovedEmployee() {

		return erepo.findAll(Sort.by(Direction.ASC,"empId")).parallelStream().filter(emp -> emp.getApproved() == false ).map(e-> {

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
			employee.setEmpPassword(null);
			employee.setEmpApproved(e.getApproved());

			return employee;
		}).collect(Collectors.toList());
	}

	@Override
	public Integer approveEmployeeRequest(Integer empId) {
		
		Optional<Employee> emp= erepo.findById(empId);

		if(emp.isPresent()) {
			Employee e = emp.get();
			e.setApproved(true);
			
			erepo.save(e);
		}
		
		return empId;
		
	}


}
