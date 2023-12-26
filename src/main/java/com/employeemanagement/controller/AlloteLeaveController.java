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
import com.employeemanagement.serviceImpl.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AlloteLeaveController {

	@Autowired
	private AllotedLeaveService allotedLeaveService;
	
	@Autowired
	private JwtUtils utils;

	@PostMapping("/admin/alloteLeave/{leave_id}")
	public ResponseEntity<Response> alloteLeave(@PathVariable("leave_id")Integer leave_id, @RequestBody AlloteLeaveRequest alloteLeaveRequest) {
		AllotedLeave alloteLeaveToEmployees = this.allotedLeaveService
				.alloteLeaveToEmployees(leave_id, alloteLeaveRequest.getEmp_ids());

		return new ResponseEntity<>(new Response(true, "leave alloted to given employees", alloteLeaveToEmployees),
				HttpStatus.OK);
	}

	@GetMapping("/employee/get_al")
	public ResponseEntity<Set<AllotedLeave>> getAllotedLeave(HttpServletRequest req) throws UserException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization"));
		Set<AllotedLeave> allotedLeaves = this.allotedLeaveService.getAllotedLeaves(usrId);

		return ResponseEntity.ok(allotedLeaves);
	}

}
