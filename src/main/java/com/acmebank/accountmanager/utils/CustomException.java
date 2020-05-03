package com.acmebank.accountmanager.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomException extends Exception {

	private String message;

	public CustomException(String message) {
		super(message);
	}

}
