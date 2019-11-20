package com.app.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.dto.AppUserDto;
import com.app.service.AppUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AppUserService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("called service");

		AppUserDto dto = service.getAppUserByUserName(username).get(0);
		
		if(dto==null)
		{
			throw new UsernameNotFoundException("User not Found");
		}
		else
		{
			Set<GrantedAuthority> authorities = new HashSet<>();
			
			dto.getUserRoles().forEach(role->authorities.add(new SimpleGrantedAuthority(role)));

			return new User(dto.getUserName(),dto.getUserPassword(),authorities);
		}
		
	}

}
