package com.employeemanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.entity.Leave;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.LeaveService;
import com.employeemanagement.serviceImpl.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LeaveController {

	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private JwtUtils utils;
	

	@GetMapping("/employee/leaves/count")
	public ResponseEntity<Map<String, Object>> getCount(HttpServletRequest req) throws UserException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization"));
		Integer totalLeaves = this.leaveService.getTotalLeavesCount(usrId);
		return ResponseEntity.ok(Map.of("Message", "success", "data", totalLeaves));
	}

	@GetMapping("/employee/leaves")
	public ResponseEntity<List<Leave>> getSortedLeaves(HttpServletRequest req,
			@RequestParam(defaultValue = "All") String category) throws UserException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization").substring(7));

		List<Leave> sortedLeaves = this.leaveService.getSortedLeaves(usrId, category);

		return ResponseEntity.ok(sortedLeaves);

	}

}
