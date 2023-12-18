package com.employeemanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.entity.Leave;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.LeaveService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LeaveController {

	@Autowired
	private LeaveService leaveService;

	

	@GetMapping("/employee/leaves/count/{emp_id}")
	public ResponseEntity<Map<String, Object>> getCount(@PathVariable("emp_id") int emp_id) throws UserException {

		Integer totalLeaves = this.leaveService.getTotalLeavesCount(emp_id);
		return ResponseEntity.ok(Map.of("Message", "success", "data", totalLeaves));
	}

	@GetMapping("/employee/leaves/{emp_id}")
	public ResponseEntity<List<Leave>> getSortedLeaves(@PathVariable("emp_id") int emp_id,
			@RequestParam(defaultValue = "All") String category) throws UserException {

		List<Leave> sortedLeaves = this.leaveService.getSortedLeaves(emp_id, category);

		return ResponseEntity.ok(sortedLeaves);

	}

}
