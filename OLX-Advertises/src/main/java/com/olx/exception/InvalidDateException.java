package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDateException extends Exception {
	private String msg;

	public InvalidDateException(String msg) {
		super();
		this.msg = msg;
	}

	public InvalidDateException() {
		super();
	}

	@Override
	public String toString() {
		return "InvalidDateException [msg=" + msg + "]";
	}
	
	
}
