package com.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.LeaveType;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.service.LeaveTypeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LeaveTypeController {

	@Autowired
	private LeaveTypeService leaveTypeService;

	@PostMapping("/admin/add_leave_type")
	public ResponseEntity<Response> createLeaveType(@RequestBody LeaveType leaveType) {

		LeaveType newLeaveType = this.leaveTypeService.addNewLeaveType(leaveType);

		return new ResponseEntity<>(new Response(true, "leave type is created", newLeaveType), HttpStatus.OK);
	}

	@GetMapping("/admin/get_all_type")
	public ResponseEntity<List<LeaveType>> getAllLeaveType() {

		List<LeaveType> allLeaveType = this.leaveTypeService.getAllLeaveType();

		return ResponseEntity.ok(allLeaveType);
	}
	
	@DeleteMapping("/admin/remove_leave_type/{leaveType_id}")
	public ResponseEntity<String> removeLeaveType(@PathVariable("leaveType_id") int leaveType_id)
			throws CustomeException{
		
		this.leaveTypeService.removeLeaveType(leaveType_id);
		
		return ResponseEntity.ok("leave type deleted ");
	}

}
