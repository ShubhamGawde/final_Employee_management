package com.employeemanagement.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Request.EmailRequest;
import com.employeemanagement.service.EmailService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.mail.MessagingException;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/api/admin/send_email")
	public ResponseEntity<String> sendEmailToUser(@RequestBody EmailRequest req) throws UnsupportedEncodingException, MessagingException {
		this.emailService.sendEmailToUsers(req);

		return ResponseEntity.ok("mail send successfully");
	}

}
