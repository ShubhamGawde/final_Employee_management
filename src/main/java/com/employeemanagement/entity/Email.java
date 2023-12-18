package com.employeemanagement.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {

	private String message;
	private String subject;
	private String to;
	private String from;
	private String senderName;

}
