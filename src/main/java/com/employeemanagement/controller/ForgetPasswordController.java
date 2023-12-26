package com.employeemanagement.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Response.ErrorResponse;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.service.EmailService;
import com.employeemanagement.service.EmployeeService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController("/api")
public class ForgetPasswordController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/employee/getotp")
	public ResponseEntity<?> getOpt(@RequestParam("email") String email, HttpSession session) {
		Random random = new Random();
		int otp = random.nextInt(10000);

		try {
			this.emailService.sendOtp(email, otp);
			session.setAttribute("OTP", otp);
			session.setAttribute("email", email);
			return ResponseEntity.ok("Otp send successfully");
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			return ResponseEntity.ok().body("Otp not send");
		}
	}

	@PostMapping("/employee/send")
	public ResponseEntity<?> getOtp(@RequestParam("OTP") int OTP, HttpSession session, HttpServletRequest req) {
		int userOtp = (int) session.getAttribute("OTP");
		String email = (String) session.getAttribute("email");
		Employee employee = this.employeeService.findEmployeeByEmail(email);

		if (employee == null) {
			return ResponseEntity.ok(new ErrorResponse(false, 404, "Invalid Email", req.getRequestURI(),
					req.getMethod(), LocalDateTime.now()));
		}

		if (OTP == userOtp) {
			return new ResponseEntity<>(new Response(true, "OTP send successfully"), HttpStatus.OK);
		}

		return new ResponseEntity<>(new ErrorResponse(false, 400, "Invalid OTP", req.getRequestURI(),
				req.getMethod(), LocalDateTime.now()), HttpStatus.OK);
	}

	@PostMapping("/change/password")
	public ResponseEntity<?> changePassword(@RequestParam("password") String password, HttpSession session) {
		String email = (String) session.getAttribute("email");
		String changePassword = employeeService.changePassword(password, email);
		return ResponseEntity.ok(changePassword);
	}

//	@PostMapping("/wd/test")
//	public long calculateDays(@RequestParam("from") String from, @RequestParam("to") String to) {
//
//		// Parse the date strings into LocalDate objects
//		LocalDate fromDate = LocalDate.parse(from);
//		LocalDate toDate = LocalDate.parse(to);
//
//		// Calculate the number of days between "from" and "to" dates
//		long daysBetween = ChronoUnit.DAYS.between(fromDate, toDate);
//
//		// Print the result
//		System.out.println("Number of days between " + from + " and " + to + " is: " + daysBetween);
//		return daysBetween;
//
//	}

}
