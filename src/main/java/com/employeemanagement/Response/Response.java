package com.employeemanagement.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response {

	private boolean success;
	private String message;
	private Object data;
	public Response(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	

}
