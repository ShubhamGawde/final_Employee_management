package com.employeemanagement.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateNoticeRequest {

	private String subject;
	private String message;
	private String issueDate;
	private String expirationDate;

}
