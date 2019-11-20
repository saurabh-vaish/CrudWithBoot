package com.app.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class AppUser implements Serializable {

	@Id
	@GeneratedValue
	private Integer userId;

	private String userName;

	private String userEmail;

	private String userPassword;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> userRoles;
	
}
