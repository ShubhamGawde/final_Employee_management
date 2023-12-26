package com.employeemanagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Request.AdminDto;
import com.employeemanagement.Request.JwtAuthenticationRequest;
import com.employeemanagement.Response.JwtAuthenticationResponse;
import com.employeemanagement.Response.Response;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.AdminService;
import com.employeemanagement.service.EmployeeService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private EmployeeService employeeService;

	@Validated
	@PostMapping("signup_admin")
	public ResponseEntity<Response> registerAdmin(@Valid @RequestBody AdminDto req, HttpServletRequest request)
			throws IOException, MessagingException, UserException {
		Response createdAdminResponse = this.adminService.createAdmin(req);

		return new ResponseEntity<>(createdAdminResponse, HttpStatus.OK);
	}


	@PostMapping("signin_admin")
	public ResponseEntity<JwtAuthenticationResponse> adminLogin(@RequestBody JwtAuthenticationRequest request)
			throws UserException {
		JwtAuthenticationResponse login = this.adminService.login(request);

		return new ResponseEntity<>(login, HttpStatus.OK);
	}

	@PostMapping("signin_employee")
	public ResponseEntity<JwtAuthenticationResponse> employeeLogin(
			@RequestBody JwtAuthenticationRequest authenticationRequest) throws UserException {
		JwtAuthenticationResponse login = this.employeeService.login(authenticationRequest);

		return new ResponseEntity<>(login, HttpStatus.OK);

	}

	@GetMapping("demo")
	public ResponseEntity<String> getString() {

		return ResponseEntity.ok("message : Ok.... ");
	}

}
