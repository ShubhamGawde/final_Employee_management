package com.employeemanagement.Request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {

	public EmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String message;
	private String subject;
	private List<String> to;
	private String from;
	private String senderName;

	public EmailRequest(String message, String subject, List<String> to, String from) {
		super();
		this.message = message;
		this.subject = subject;
		this.to = to;
		this.from = from;

	}

}
