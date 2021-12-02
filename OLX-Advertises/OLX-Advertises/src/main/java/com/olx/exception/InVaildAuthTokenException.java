package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InVaildAuthTokenException extends RuntimeException  {
	
	
		private String msg;

		public  InVaildAuthTokenException() {
			this.msg = "";
		}

		public  InVaildAuthTokenException(String msg) {
			this.msg = msg;
		}

		@Override
		public String toString() {
			return "Invalid Token id " + this.msg;
		}


}
