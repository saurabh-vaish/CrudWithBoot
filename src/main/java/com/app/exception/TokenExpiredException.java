package com.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED,reason = "token expired")
public class TokenExpiredException extends RuntimeException {

	public TokenExpiredException() {
		super();
	}
	
}
