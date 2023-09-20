package com.employeemanagement.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.employeemanagement.entity.Admin;
import com.employeemanagement.entity.Attendence;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.model.JwtAuthenticationRequest;
import com.employeemanagement.model.JwtAuthenticationResponse;
import com.employeemanagement.model.Response;
import com.employeemanagement.service.AdminService;
import com.employeemanagement.service.AttendenceService;
import com.employeemanagement.service.MonthlyLeaveRecordService;
import com.employeemanagement.service.EmailService;
import com.employeemanagement.service.EmployeeService;
//import com.employeemanagement.utility.RequestURL;
import com.employeemanagement.service.LeaveService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AttendenceService attendenceService;

	@Autowired
	private LeaveService leaveService;

	@Autowired
	private MonthlyLeaveRecordService databaseService;

	// Sign up Handler

	@Validated
	@PostMapping("/wd/signup_admin")
	public ResponseEntity<Response> registerAdmin(@Valid @RequestBody Admin admin, HttpServletRequest request)
			throws IOException, MessagingException {

		Admin findByEmail = this.adminService.findByEmail(admin.getEmail());

		if (findByEmail != null) {

			return new ResponseEntity<>(new Response(false, "email already exists", 409, "", List.of("Conflict"), null),
					HttpStatus.CONFLICT);

		}

//			admin.setAdminImage(file.getBytes());

		JwtAuthenticationResponse createdAdminResponse = this.adminService.createAdmin(admin);
		Admin createdAdmin = (Admin) createdAdminResponse.getUser();

//		String siteURL = RequestURL.getSiteURL(request);

//			emailService.sendVerification(createdAdminResponse.getUser, siteURL);

		return new ResponseEntity<>(new Response(true, "Admin created successfully", 201,
				createdAdminResponse.getJwtToken(), null, createdAdmin), HttpStatus.CREATED);
	}

	// Admin verification handler

	@GetMapping("/wd/verify")
	public String verified(@RequestParam("code") String code) throws UnsupportedEncodingException {

		Admin admin = this.adminService.verify(code);

		if (admin != null) {

			this.emailService.sendVerifiedMailToAdmin(admin);

			System.out.println("mail testing");

		}

		return "you verified the admin for login process";

	}

	// Login Handler

	@PostMapping("/wd/signin_admin")
	public ResponseEntity<Response> login(@RequestBody JwtAuthenticationRequest request) {
		Admin admin = this.adminService.findByEmail(request.getEmail());

		if (admin.isActive()) {

			return new ResponseEntity<>(
					new Response(false, "You are loggedin in another device logout first from this device", 401, "",
							List.of("UNAUTHORIZED"), null),
					HttpStatus.UNAUTHORIZED);

		}

		try {
			JwtAuthenticationResponse loginResponse = this.adminService.login(request, admin);

			return new ResponseEntity<>(new Response(true, "login successfully", 200, loginResponse.getJwtToken(), null,
					loginResponse.getUser()), HttpStatus.OK);

		} catch (DisabledException ex) {

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

	// for showing profile

	@GetMapping("/admin/profile")
	public ResponseEntity<Response> getAdmin(Principal principal) {

		try {
			String email = principal.getName();

			Admin admin = this.adminService.findByEmail(email);

			return new ResponseEntity<>(new Response(true, "Admin found", 302, "", null, admin), HttpStatus.FOUND);

		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(new Response(false, "User not found", 404, "", List.of("NOT_FOUND"), null),
					HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/admin/update_admin/{id}")
	public ResponseEntity<Response> updateAdminRecord(@PathVariable("id") int id, @RequestBody Admin newAdminRecord) {

		Admin admin = this.adminService.getAdmin(id);

		if (admin == null) {

			return new ResponseEntity<>(new Response(false, "Invalid Employee", 404, null, List.of("NOT_FOUND"), null),
					HttpStatus.NOT_FOUND);
		}

		try {
			Admin updatedEmployee = this.adminService.updateDetails(newAdminRecord, admin);
			return new ResponseEntity<>(new Response(true, " updated successfully", 200, null, null, updatedEmployee),
					HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(
					new Response(false, " email already exists", 409, null, List.of("CONFLICT"), newAdminRecord),
					HttpStatus.OK);
		}

	}

	// Employee Creation Handler

	@Validated
	@PostMapping("/admin/add_employee")
	public ResponseEntity<Response> createEmployee(@Valid @RequestBody Employee employee, HttpServletRequest request) {

		Employee checkEmployee = this.employeeService.checkEmployee(employee.getEmail());

		if (checkEmployee != null) {

			return new ResponseEntity<>(new Response(false, "email already exists", 409, "", List.of("CONFLICT"), null),
					HttpStatus.CONFLICT);

		}

		JwtAuthenticationResponse jwtAuthenticationResponse = this.employeeService.createEmployee(employee);

		return new ResponseEntity<>(
				new Response(true, " Created successfully", 201, "", null, jwtAuthenticationResponse.getUser()),
				HttpStatus.CREATED);

	}

	// Read single Employee handler

	@GetMapping("/admin/read_employee/{id}")
	public ResponseEntity<Response> displaySingleEmployeeRecord(@PathVariable("id") int id) {

		Employee singleEmployee = this.employeeService.getSingleEmployee(id);
		if (singleEmployee == null) {

			return new ResponseEntity<>(
					new Response(false, " Employee not found", 404, null, List.of("NOT_FOUND"), null),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new Response(true, " Found ", 200, null, null, singleEmployee), HttpStatus.OK);

	}

	// Read All Employee handler

	@GetMapping("/admin/read_employees")
	public ResponseEntity<List<Employee>> displayAllEmployeesOnAdminPanel() {

		List<Employee> allEmployee = this.employeeService.getAllEmployee();

		return ResponseEntity.ok(allEmployee);

	}

	// Delete Employee Handler

	@DeleteMapping("/admin/delete_employee/{id}")
	public ResponseEntity<Response> removeEmployeeRecord(@PathVariable("id") int id) {

		Employee employee = this.employeeService.getSingleEmployee(id);
		if (employee == null) {

			return new ResponseEntity<>(
					new Response(false, "Employee not found", 404, null, List.of("NOT_FOUND"), null),
					HttpStatus.NOT_FOUND);
		}

		Employee removedEmployee = this.employeeService.removeEmployee(employee);

		return new ResponseEntity<>(new Response(true, " Deleted successfully", 200, null, null, removedEmployee),
				HttpStatus.OK);
	}

	// Update Employee Record Handler

	@PutMapping("/admin/update_employee/{id}")
	public ResponseEntity<Response> updateEmployeeRecord(@PathVariable("id") int id,
			@RequestBody Employee newEmployeeRecord) {

		Employee getEmployeeById = this.employeeService.getEmployeeById(id);

		if (getEmployeeById == null) {

			return new ResponseEntity<>(new Response(false, "Invalid Employee", 404, null, List.of("NOT_FOUND"), null),
					HttpStatus.NOT_FOUND);
		}

		try {
			Employee updatedEmployee = this.employeeService.updateEmployeeRecords(newEmployeeRecord, getEmployeeById);
			return new ResponseEntity<>(new Response(true, " updated successfully", 200, null, null, updatedEmployee),
					HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(
					new Response(false, " email already exists", 409, null, List.of("CONFLICT"), newEmployeeRecord),
					HttpStatus.OK);
		}

	}

	// for showing all the present employees

	@GetMapping("/admin/present_employee")
	public ResponseEntity<List<Attendence>> numberOfPresentEmployee() {

		List<Attendence> allPresentEmployee = this.attendenceService.getAllPresentEmployee();

		return ResponseEntity.ok(allPresentEmployee);

	}

//	show leave application of employees

	@GetMapping("/admin/applications")
	public ResponseEntity<Map<String, Object>> showAllApplications() {

		Map<String, Object> showAllApplication = this.leaveService.showAllApplication();

		return new ResponseEntity<>(showAllApplication, HttpStatus.OK);

	}

	// Handler for handling hr response

	@PostMapping("/admin/leave_response")
	public ResponseEntity<Map<String, Object>> leaveApplicationReponse(@RequestBody LeaveApplication leaveApplication) {

		Map<String, Object> reponse = this.leaveService.setStatus(leaveApplication);

		return new ResponseEntity<>(reponse, HttpStatus.OK);

	}

	@GetMapping("/admin/generate_report")
	public ResponseEntity<Map<String, Object>> insert() {

		Map<String, Object> response = new HashMap<>();
		try {
			this.databaseService.executeSQLQueries();
			response.put("success", true);
			response.put("code", 200);
			response.put("message", "record inserted sucessfully");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception ex) {
			response.put("success", false);
			response.put("code", 500);
			response.put("message", "record not inserted ");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/admin/logout/{id}")
	public ResponseEntity<Map<String, Object>> logout(@PathVariable("id") int id) {

		this.adminService.logout(id);
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("code", 200);
		response.put("message", "logout successfully");

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/wd/demo")
	public String demo() {

		this.databaseService.executeSQLQueries();
		return "success";

	}

}