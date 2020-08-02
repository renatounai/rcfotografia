package com.rcfotografia.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PhotoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public PhotoException( String message, Exception cause) {
		super(message, cause);
	}

}
