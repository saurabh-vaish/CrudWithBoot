package com.app.controller.rest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.constants.AppConstants;
import com.app.dto.AppUserDto;
import com.app.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;


	@Autowired
	private StringRedisTemplate redisTemplate;   		// spring provided class to communicate with redis


	@PostMapping(value = "/checkLogin")
	public ResponseEntity<Object> checkLogin(@RequestBody AppUserDto appuser)
	{
		String username = appuser.getUserName();
		String password = appuser.getUserPassword();

		String normal = "no";
		// returns authentication if passed authentication object is valid i.e. username & password are valid

		Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

		try 
		{
			final Authentication authentication =	authenticationManager.authenticate(auth);

			// setting this auth obj to security context
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// generating new token using this generated auth object
			final String token = jwtTokenProvider.createToken(authentication);

			// storing token with username [email] in redis server
			redisTemplate.opsForValue().set(username, token, Duration.ofMillis(AppConstants.validityInMilliseconds)); 

			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
			{
				normal = "yes";
			}
			
			Map<String,String> m = new HashMap<>();
			m.put("token", token);
			m.put("normal", normal);
			
			return ResponseEntity.ok(m);
		}
		catch (BadCredentialsException e)
		{
			throw new BadCredentialsException("Invalid Username & Password");
		}
	}


	@GetMapping("/doLogout")
	public String afterLogout()
	{
		return "logged successfully";
	}
}
