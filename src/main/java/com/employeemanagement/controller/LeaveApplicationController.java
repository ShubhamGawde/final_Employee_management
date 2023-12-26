package com.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Request.LeaveApplicationRequest;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.entity.LeaveStatus;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.serviceImpl.JwtUtils;
import com.employeemanagement.serviceImpl.LeaveApplicationServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LeaveApplicationController {
	
	@Autowired
	private JwtUtils utils;

	@Autowired
	private LeaveApplicationServiceImpl leaveApplicationService;

//show leave application of employees
	@GetMapping("/admin/leave_applications")
	public ResponseEntity<?> showAllApplications() {
		List<LeaveApplication> showAllApplication = this.leaveApplicationService.showAllApplication();

		return new ResponseEntity<>(showAllApplication, HttpStatus.OK);
	}

	@PostMapping("/employee/create_application")
	public ResponseEntity<Response> applyForLeave(@RequestBody LeaveApplicationRequest leaveApplication)
			throws UserException {
		LeaveApplication creatingLeaveApplication = this.leaveApplicationService
				.creatingLeaveApplication(leaveApplication);

		return new ResponseEntity<Response>(new Response(true, "leave application applied", creatingLeaveApplication),
				HttpStatus.OK);
	}

	@GetMapping("/employee/leave_applications")
	public ResponseEntity<List<LeaveApplication>> showLeaveApplicationStatus(HttpServletRequest req)
			throws UserException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization"));
		List<LeaveApplication> applicationStatus = this.leaveApplicationService.getEmployeeApplicationByEmpId(usrId);

		return new ResponseEntity<>(applicationStatus, HttpStatus.OK);

	}

	// Handler for handling hr response

	@PostMapping("/admin/application_response/{app_id}/{status}")
	public ResponseEntity<Response> leaveApplicationReponse(@PathVariable("app_id") int app_id,
			@PathVariable("status") LeaveStatus status) throws CustomeException {
		Response reponse = this.leaveApplicationService.setLeaveStatus(app_id, status);

		return new ResponseEntity<>(reponse, HttpStatus.OK);
	}

}
