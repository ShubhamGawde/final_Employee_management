package com.employeemanagement.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.model.JwtAuthenticationRequest;
import com.employeemanagement.model.JwtAuthenticationResponse;
import com.employeemanagement.model.Response;
import com.employeemanagement.service.AttendenceService;
import com.employeemanagement.service.EmployeeService;
//import com.employeemanagement.utility.RequestURL;
import com.employeemanagement.service.LeaveService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	AttendenceService attendenceService;

	@Autowired
	private LeaveService leaveService;

	@Validated
	@PostMapping("/wd/signup_employee")
	public ResponseEntity<Response> registerEmployee(@Valid @RequestBody Employee employee,
			HttpServletRequest httpRequest) throws IOException {

		Employee findByEmail = this.employeeService.checkEmployee(employee.getEmail());
//		System.out.println(findByEmail);

		if (findByEmail != null) {

			return new ResponseEntity<>(new Response(false, "email already exists", 409, "", List.of("CONFLICT"), null),
					HttpStatus.CONFLICT);

		}

		JwtAuthenticationResponse createdEmployeeReponse = this.employeeService.createEmployee(employee);
		Employee createdEmp = (Employee) createdEmployeeReponse.getUser();

//		String siteURL = RequestURL.getSiteURL(httpRequest);

//		emailService.sendVerification(createdEmp, siteURL);

		return new ResponseEntity<>(new Response(true, " created successfully", 201,
				createdEmployeeReponse.getJwtToken(), null, createdEmp), HttpStatus.CREATED);

	}

	@PostMapping("/wd/signin_employee")
	public ResponseEntity<Response> login(@RequestBody JwtAuthenticationRequest request) {

		Employee employee = this.employeeService.checkEmployee(request.getEmail());

		if (employee.isActive()) {

			return new ResponseEntity<>(
					new Response(false, "You are logged in another device logout first from this device ", 401, "",
							List.of("UNAUTHORIZED"), null),
					HttpStatus.UNAUTHORIZED);

		}

		try {
			JwtAuthenticationResponse loginResponse = this.employeeService.login(request, employee);

			return new ResponseEntity<>(new Response(true, "login successfully", 200, loginResponse.getJwtToken(), null,
					loginResponse.getUser()), HttpStatus.OK);
		} catch (

		DisabledException ex) {

			System.out.println("User Disabled" + ex.getMessage());
			return new ResponseEntity<>(
					new Response(false, "you are not allowed", 400, null, List.of("INTERNAL_SERVER_ERROR"), null),
					HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (BadCredentialsException ex) {

			System.out.println("BadCredentials" + ex.getMessage());

			return new ResponseEntity<>(
					new Response(false, "invalid id or password", 400, null, List.of("BAD_REQUEST"), null),
					HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/employee/profile")
	public ResponseEntity<Response> getEmployee(Principal principal) {

		try {

			String email = principal.getName();

			Employee employee = this.employeeService.checkEmployee(email);

			return new ResponseEntity<>(new Response(true, "Employee found", 302, "", null, employee),
					HttpStatus.FOUND);

		} catch (NoSuchElementException ex) {
//			ex.printStackTrace();
			return new ResponseEntity<>(new Response(false, "User not found", 404, "", List.of("NOT_FOUND"), null),
					HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/employee/check_in/{id}")
	public ResponseEntity<Map<String, Object>> checkIn(@PathVariable("id") int id) throws Exception {

		this.employeeService.getSingleEmployee(id);

		Map<String, Object> response = new HashMap<>();

		boolean addAttendence = this.attendenceService.addAttendence(id);
		if (addAttendence) {

			response.put("success", true);
			response.put("code", 200);
			response.put("message", "Attenedence Registered Successfully");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {

			System.out.println("Attendence Already Registered");
			response.put("success", false);
			response.put("code", 409);
			response.put("message", "Attenedence Already Registered ");
			response.put("error", "CONFLICT");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

	}

	@PostMapping("/employee/check_out/{id}")
	public ResponseEntity<Map<String, Object>> checkOut(@PathVariable("id") int id) throws Exception {

		this.employeeService.getSingleEmployee(id);

		Map<String, Object> response = new HashMap<>();

		int setCheckOut = this.attendenceService.setCheckOut(id);

		if (setCheckOut == 1) {

			response.put("success", true);
			response.put("code", 200);
			response.put("message", "Out time is added");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else if (setCheckOut == 0) {

			response.put("success", false);
			response.put("code", 400);
			response.put("message", "Check in first");
			response.put("error", "BAD_REQUEST");

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		} else {

			response.put("success", false);
			response.put("code", 409);
			response.put("message", "out time is already added");
			response.put("error", "CONFLICT");

			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

	}

	@PostMapping("/employee/apply")
	public ResponseEntity<Response> applyForLeave(@RequestBody LeaveApplication leaveApplication) {

//		LeaveApplication creatingLeaveApplication = this.leaveService.creatingLeaveApplication(leaveApplication);

		System.out.println(leaveApplication.getStart_date());
		System.out.println(leaveApplication.getEnd_date());

		return new ResponseEntity<Response>(new Response(true, "leave application applied", 201, "", null, null),
				HttpStatus.CREATED);

	}

	// for showing leave application status of employee

	@GetMapping("/employee/leave_applications/{emp_id}")
	public ResponseEntity<List<LeaveApplication>> showLeaveApplicationStatus(@PathVariable("emp_id") int emp_id) {
		List<LeaveApplication> applicationStatus = this.leaveService.getApplicationStatus(emp_id);

		return new ResponseEntity<>(applicationStatus, HttpStatus.OK);

	}

	@PutMapping("/employee/logout")
	public ResponseEntity<Map<String, Object>> logout(@PathVariable("id") int id) {

		this.employeeService.logout(id);
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("code", 200);
		response.put("message", "logout successfully");

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/wd/demo/{id}")
	public String demo(@PathVariable("id") int id) {

		return this.attendenceService.demo(id);

	}

}
