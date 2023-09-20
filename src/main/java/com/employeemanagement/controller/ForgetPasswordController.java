package com.employeemanagement.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.entity.Employee;
import com.employeemanagement.service.EmailService;
import com.employeemanagement.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
public class ForgetPasswordController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/getotp")
	public ResponseEntity<?> sendOpt(@RequestParam("email") String email, HttpSession session) {

		Random random = new Random();
		int otp = random.nextInt(10000);

		try {
			this.emailService.sendOtp(email, otp);
			session.setAttribute("OTP", otp);
			session.setAttribute("email", email);

			return ResponseEntity.ok("Otp send successfully");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Otp not send");
		}

	}

	@PostMapping("/send")
	public ResponseEntity<?> getOtp(@RequestParam("OTP") int OTP, HttpSession session) {
		Map<String, Object> response = new HashMap<>();

		int userOtp = (int) session.getAttribute("OTP");
		String email = (String) session.getAttribute("email");
		Employee employee = this.employeeService.checkEmployee(email);
		if (employee == null) {

			response.put("success", false);
			response.put("code", 500);
			response.put("message", "Email is invalid");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		} else if (OTP == userOtp) {

			response.put("success", true);
			response.put("code", 200);
			response.put("message", "OTP matched");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {

			response.put("success", false);
			response.put("code", 500);
			response.put("message", " invalid OTP");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	public ResponseEntity<?> changePassword(@RequestParam("password") String password, HttpSession session) {

		Map<String, Object> response = new HashMap<>();
		String email = (String) session.getAttribute("email");
		Employee employee = this.employeeService.checkEmployee(email);
		employee.setPassword(passwordEncoder.encode(password));
		employeeService.save(employee);
		response.put("success", true);
		response.put("code", 200);
		response.put("message", " Password changed Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/wd/test")
	public long calculateDays(@RequestParam("from") String from, @RequestParam("to") String to) {

		// Parse the date strings into LocalDate objects
		LocalDate fromDate = LocalDate.parse(from);
		LocalDate toDate = LocalDate.parse(to);

		// Calculate the number of days between "from" and "to" dates
		long daysBetween = ChronoUnit.DAYS.between(fromDate, toDate);

		// Print the result
		System.out.println("Number of days between " + from + " and " + to + " is: " + daysBetween);
		return daysBetween;

	}

}
