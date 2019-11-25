package com.app.jwt;

import com.app.constants.AppConstants;
import com.app.util.EncoderDecoderUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * This class is used to generate , validate jwt token and returns Authentication for user
 * 
 * */

@Component
public class JwtTokenProvider {

	private String secretKey = "mySecret";

	@Autowired
	private EncoderDecoderUtil util;

	/**
	 * Encoded secret 
	 * */
	@PostConstruct
	public void init()
	{
		this.secretKey = Base64.getEncoder().encodeToString(AppConstants.tokenSecret.getBytes());
	}



	/**
	 *  Method to generate token with given user  
	 *  @param username
	 *  @param roles
	 *  @return generated token
	 * 
	 * **/

	public String createToken(Authentication authentication)
	{
		String username = authentication.getName();   // getting username from authentication  object

		// getting user roles from auth object 
		String roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

		// setting claims for user
		Claims claims = Jwts.claims().setSubject(username);

		// adding roles to token
		claims.put(AppConstants.tokenAuthorities,roles);

		// creating date
		Date creationDate = new Date(System.currentTimeMillis());

		// token validity
		Date validity = new Date(creationDate.getTime() + AppConstants.validityInMilliseconds);


		// returing build token
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(creationDate)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();

	}


	/**
	 * Method to check token is present or not
	 * 
	 * @param HttpServetRequest 
	 * @return token or null
	 * 
	 * */

	public String returnTokenIfExist(HttpServletRequest req)
	{
		// get token header , return null if not present
		String authHeader = req.getHeader("Authorization");

		if(authHeader !=null && authHeader.startsWith(AppConstants.startWith))  // check token not null & start with given prefix
		{
			return authHeader.substring(AppConstants.startWith.length(),authHeader.length());  // return token without prefix
		}
		else return null;

	}


	public String getNameFromHeader(HttpServletRequest req) throws Exception {
		// get token header , return null if not present
		String name = req.getHeader("UserName");

		String nameHeader = name !=null ? util.decrypt(name) : null;

		if(nameHeader !=null)  // check token not null & start with given prefix
		{
			return nameHeader;
		}
		else return null;
	}




	/**
	 * Method to validate token 
	 * 
	 * @param String token
	 * @param UserDetails
	 * @return boolean
	 * 
	 * **/

	public Boolean validateToken(String token,UserDetails userDetails,String name)
	{
		// getting username from token
		String userName = getUserNameFromToken(token);
		
		if(StringUtils.isEmpty(name) && !userName.equals(name))
		{
			return false;
		}
		else {
			// validating username from current login and token expiry
			return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}

	}



	public Claims getClaimsFromToken(String token)
	{
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}



	public Boolean isTokenExpired(String token)
	{
		return getClaimsFromToken(token).getExpiration().before(new Date());
	}



	public String getUserNameFromToken(String token)
	{
		return getClaimsFromToken(token).getSubject();
	}


	/**
	 * getting authentication of user with given user details and authorities from token
	 * 
	 * @param Token
	 * @param UserDetails
	 * @return AuthenticationToken
	 * 
	 * **/

	public UsernamePasswordAuthenticationToken getAuthentication(final String token,final UserDetails userDetails)
	{
		// getting claims from user
		final Claims claims = getClaimsFromToken(token);


		// getting userroles from token and setting in authorities


		final List<GrantedAuthority> authorities = Arrays.stream(claims.get(AppConstants.tokenAuthorities).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());


		// return authentication of user with given user details and authorities from token
		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

	}




}
