package com.employeemanagement.model;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String jwtToken;
	private Object user;

	public JwtAuthenticationResponse(String jwtToken, Object user) {
		this.jwtToken = jwtToken;
		this.user = user;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

}
