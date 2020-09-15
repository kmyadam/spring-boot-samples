package com.poc.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 2081981528157024809L;
	
	private final String jwt;
    private final String type = "Bearer";

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	/**
	 * @return the jwt
	 */
	public String getJwt() {
		return jwt;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + ", type=" + type + "]";
	}

}
