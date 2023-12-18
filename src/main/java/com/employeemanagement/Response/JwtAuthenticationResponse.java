package com.employeemanagement.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtAuthenticationResponse {

	private boolean success;
	private String message;
	private String jwtToken;
	private Object data;

}
