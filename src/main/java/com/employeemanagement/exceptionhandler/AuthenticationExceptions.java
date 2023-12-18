package com.employeemanagement.exceptionhandler;

import jakarta.servlet.ServletException;


public class AuthenticationExceptions extends ServletException {

	private static final long serialVersionUID = 1L;

	public AuthenticationExceptions(String msg) {
		super(msg);
	}

}
