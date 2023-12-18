package com.employeemanagement.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomeException extends Exception {

	private static final long serialVersionUID = 1L;

	private boolean success;
	private String message;
	private int status;

}
