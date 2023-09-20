package com.employeemanagement.model;

import java.util.List;

public class Response {

	private boolean success;
	private String message;
	private int status;
	private String jwtToken;
	private List<String> error;
	private Object data;

	public Response() {
		super();
	}

	public Response(boolean success, String message, int status, String jwtToken, List<String> error, Object data) {
		super();
		this.success = success;
		this.status = status;
		this.jwtToken = jwtToken;
		this.message = message;
		this.error = error;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

}
