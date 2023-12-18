package com.employeemanagement.controller;

import java.util.Set;

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

import com.employeemanagement.Request.AlloteLeaveRequest;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.AllotedLeave;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.AllotedLeaveService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AlloteLeaveController {

	@Autowired
	private AllotedLeaveService allotedLeaveService;

	@PostMapping("/admin/alloteLeave")
	public ResponseEntity<Response> alloteLeave(@RequestBody AlloteLeaveRequest alloteLeaveRequest) {
		AllotedLeave alloteLeaveToEmployees = this.allotedLeaveService
				.alloteLeaveToEmployees(alloteLeaveRequest.getLeaveType(), alloteLeaveRequest.getEmp_ids());

		return new ResponseEntity<>(new Response(true, "leave alloted to given employees", alloteLeaveToEmployees),
				HttpStatus.OK);
	}

	@GetMapping("/employee/get_al/{emp_id}")
	public ResponseEntity<Set<AllotedLeave>> getAllotedLeave(@PathVariable("emp_id") int emp_id) throws UserException {
		Set<AllotedLeave> allotedLeaves = this.allotedLeaveService.getAllotedLeaves(emp_id);

		return ResponseEntity.ok(allotedLeaves);
	}

}
