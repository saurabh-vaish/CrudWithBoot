package com.app.dto;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

	private Integer userId;

	private String userName;

	private String userEmail;

	private String userPassword;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> userRoles;
	
}
