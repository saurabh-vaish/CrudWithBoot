package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.AppUserDto;
import com.app.model.AppUser;
import com.app.repo.AppUserRepository;
import com.app.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository repo;

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
		
//		AppUser savedUser = repo.save(app);

//		user.setUserId(savedUser.getUserId());
		user.setUserId(1);
		
		return user;
	}

	@Override
	@Transactional
	public Integer updateAppUser(AppUserDto user) {

		AppUser app = new AppUser();
		app.setUserId(user.getUserId());
		app.setUserName(user.getUserName());
		app.setUserEmail(user.getUserEmail());
		app.setUserPassword(user.getUserPassword());
		app.setUserRoles(user.getUserRoles());
		
		AppUser savedUser = repo.save(app);

		user.setUserId(savedUser.getUserId());
		
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
			app.setUserEmail(user.getUserEmail());
			app.setUserName(user.getUserName());
			app.setUserPassword(user.getUserPassword());
			app.setUserRoles(user.getUserRoles());
			
			return app;
		}).collect(Collectors.toList());
	}

	@Override
	public AppUserDto getOneAppUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}


}
