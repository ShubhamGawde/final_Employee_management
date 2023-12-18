package com.employeemanagement.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserException extends Exception {

	private static final long serialVersionUID = 1L;
	private boolean success;
	private String message;
	private int status;

}
