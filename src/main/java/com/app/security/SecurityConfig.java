package com.app.security;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.constants.AppConstants;
import com.app.exception.ApiError;
import com.app.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private SCryptPasswordEncoder enc;
	
	@Autowired
	private UserDetailsService userDetails;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	
	@Bean
	public JwtRequestFilter jwtRequestFilterBean()
	{
		return new JwtRequestFilter();
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetails).passwordEncoder(enc);
			
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.cors()
		.and()
		.httpBasic().disable()
		.csrf().disable()
//		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//		.and()
		.authorizeRequests()
		.antMatchers("/auth/checkLogin").permitAll()
		.antMatchers("/admin/count").permitAll()
		.antMatchers("/user/register","/user/check").permitAll()
		.antMatchers("/admin/**").hasAuthority("ADMIN")
		.antMatchers("/updateEmployee","/delete/*").hasAuthority("ADMIN")
		
		.anyRequest().authenticated()
		
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		.and()
		.logout().logoutUrl("/auth/logout").logoutSuccessHandler(myLogoutHandler())
		.invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.permitAll()
		
		.and()
		.exceptionHandling().authenticationEntryPoint(unathorizedEntryPoint()) ;
		
		// adding jwt filter before process request
		http.addFilterBefore(jwtRequestFilterBean(), UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
	
	// on logout
	public LogoutSuccessHandler myLogoutHandler() {

		return new LogoutSuccessHandler() {
			
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
					throws IOException, ServletException {

				String username = request.getHeader("UserName");
				
				if(!StringUtils.isEmpty(username))
				{
					redisTemplate.opsForValue().set(username, "invalid",Duration.ofMillis(AppConstants.validityInMilliseconds));	
				}
				
			}
			
		};  
	}


	// unauthorized 
	
	@Bean
	public AuthenticationEntryPoint unathorizedEntryPoint() {
		// calling commence() of AuthenticationEntryPoint
		return (request, response, authException) -> { 
			
			String username = request.getHeader("UserName");
			
			if(!StringUtils.isEmpty(username))
			{
				redisTemplate.opsForValue().set(username, "invalid",Duration.ofMillis(AppConstants.validityInMilliseconds));	
			}
			
			ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), AppConstants.invalidToken ,LocalDateTime.now());

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,error.toString());
		};
	}

	
	// for cross origin
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		 CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE"));
	        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization", "UserName"));
//	        configuration.setAllowedHeaders(Arrays.asList("X-XSRF-TOKEN", "XSRF-TOKEN"));
	        configuration.setAllowCredentials(true);
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	}



	
}
