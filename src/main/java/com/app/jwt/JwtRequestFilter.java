package com.app.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.constants.AppConstants;
import com.app.exception.ApiError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;


/**
 * 
 *  Stops request and check request and token
 * 
 * **/

public class JwtRequestFilter extends OncePerRequestFilter{

	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	private String errorJson ="";
	
	
	// process request
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,  FilterChain filterChain)
			throws ServletException, IOException {
	
		String nameHeader = tokenProvider.getNameFromHeader(request);

		try {			
			//1. getting token from request
			String token = tokenProvider.returnTokenIfExist(request);
			
			if(token !=null) 
			{
				if(!StringUtils.isEmpty(nameHeader))
				{
					 String value = redisTemplate.opsForValue().get(nameHeader);
					
					if(!StringUtils.isEmpty(value) && !value.equals("invalid"))				// token is present in regis db
					{
						if (!tokenProvider.isTokenExpired(token)) // if token not expired
						{
							String userName = tokenProvider.getUserNameFromToken(token);
												
							UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
								
								if(tokenProvider.validateToken(token, userDetails,nameHeader))   // token is valid
								{
									UsernamePasswordAuthenticationToken auth = tokenProvider.getAuthentication(token, userDetails);
									
									auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
									
									SecurityContextHolder.getContext().setAuthentication(auth); 	// setting authentication 
									
								}
								else { sendResponse(AppConstants.invalidToken); }
						}
						else { sendResponse(AppConstants.expiredToken); }
					}
					else { sendResponse(AppConstants.invalidToken); throw new RuntimeException("explicitly thrown"); }
				}
				else { sendResponse(AppConstants.noHeader); throw new RuntimeException();}
			}
			
		}
		catch( Exception e)
		{
			System.out.println(e );
			
			// setting username to null if token is not correct for that user if any exception occur 
			if(!StringUtils.isEmpty(nameHeader)) {
				redisTemplate.opsForValue().set(nameHeader, "invalid",Duration.ofMillis(AppConstants.validityInMilliseconds));
				
				sendResponse(AppConstants.invalidToken);
			}
			
			HttpServletResponse httpresposne = response;
			
			httpresposne.setContentType("application/json");
			httpresposne.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			
			PrintWriter out = httpresposne.getWriter();
			
			out.write(this.errorJson);
			
			out.flush();
			out.close();
			
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	
	// method for generating error messsage
	private void sendResponse(String msg) throws JsonProcessingException
	{
		
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), msg ,LocalDateTime.now());
		
		this.errorJson = new ObjectMapper().writeValueAsString(error);
		
				
	}
	
}
