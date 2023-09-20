package com.employeemanagement.model;

import org.springframework.stereotype.Component;

@Component
public class Email {

	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String message;
	private String subject;
	private String to;
	private String from;
	private String senderName;
	private String replyTo;

	public Email(String message, String subject, String to, String from, String senderName, String replyTo) {
		super();
		this.message = message;
		this.subject = subject;
		this.to = to;
		this.from = from;
		this.senderName = senderName;
		this.replyTo = replyTo;
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

}
