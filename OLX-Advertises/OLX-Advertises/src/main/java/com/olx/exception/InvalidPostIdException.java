package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPostIdException extends RuntimeException {

	private String msg;

	public InvalidPostIdException() {
		this.msg = "";
	}

	public InvalidPostIdException(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Invalid Post id " + this.msg;
	}

}
