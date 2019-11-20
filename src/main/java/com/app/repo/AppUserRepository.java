package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer>{

	List<AppUser> findByUserName(String userName);
	
}
