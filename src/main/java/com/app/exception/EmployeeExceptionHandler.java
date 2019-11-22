package com.app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeExceptionHandler {
	
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ApiError> handleEmployeeNotFoundException()
	{
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(),"No Employee Found Of Given Id" , LocalDateTime.now());
		
		return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AppUserNotFoundException.class)
	public ResponseEntity<ApiError> handleAppUserNotFoundException()
	{
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(),"No AppUser Found Of Given Id" , LocalDateTime.now());
		
		return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> handleInvalidCredentialException()
	{
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(),"Invalid UserName & Password" , LocalDateTime.now());
		
		return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
	}

	
}
