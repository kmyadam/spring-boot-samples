package com.poc.exception;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -2404530774351365007L;

	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
