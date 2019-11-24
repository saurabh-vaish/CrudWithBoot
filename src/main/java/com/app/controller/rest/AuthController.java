package com.app.controller.rest;

import com.app.constants.AppConstants;
import com.app.dto.AppUserDto;
import com.app.jwt.JwtTokenProvider;
import com.app.util.EncoderDecoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private EncoderDecoderUtil util;

	@Autowired
	private StringRedisTemplate redisTemplate;   		// spring provided class to communicate with redis


	@PostMapping(value = "/checkLogin")
	public ResponseEntity<Object> checkLogin(@RequestBody AppUserDto appuser)
	{
		try {

		String username = util.decrypt(appuser.getUserName());
			System.out.println(username);
		String password = util.decrypt(appuser.getUserPassword());
			System.out.println(password);

		// returns authentication if passed authentication object is valid i.e. username & password are valid
		Authentication auth = new UsernamePasswordAuthenticationToken(username, password);


			final Authentication authentication =	authenticationManager.authenticate(auth);

			// setting this auth obj to security context
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// generating new token using this generated auth object
			final String token = jwtTokenProvider.createToken(authentication);

			// storing token with username [email] in redis server
			redisTemplate.opsForValue().set(username, token, Duration.ofMillis(AppConstants.validityInMilliseconds));

			String normal = "no";
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
		catch (Exception e)
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
