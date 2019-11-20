package com.app.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {

	private Integer errorCode;
	private String errorDesc;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime errorDate;

	public ApiError(Integer errorCode, String errorDesc, LocalDateTime errorDate) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.errorDate = errorDate;
	}
	
	
	
	
}
