package com.employeemanagement.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Attendence;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.serviceImpl.AttendenceServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AttendenceController {

	@Autowired
	AttendenceServiceImpl attendenceService;

	@GetMapping("/employee/ischeck_in/{id}")
	public ResponseEntity<Map<String, Object>> isCheckedIn(@PathVariable("id") int id) throws UserException {
		Boolean checkIn = this.attendenceService.isCheckIn(id);

		return ResponseEntity.ok(Map.of("Success", true, "Status",200, "CheckedIN", checkIn));

	}

	@PostMapping("/employee/check_in/{id}")
	public ResponseEntity<Response> checkIn(@PathVariable("id") int id) throws UserException, CustomeException {
		Attendence addAttendence = this.attendenceService.setChecking(id);

		return new ResponseEntity<>(new Response(true, "Attendence and checkin successfully", addAttendence),
				HttpStatus.OK);
	}

	@PostMapping("/employee/check_out/{id}")
	public ResponseEntity<Response> checkOut(@PathVariable("id") int id) throws UserException, CustomeException {
		Attendence setCheckOutResponse = this.attendenceService.setCheckOut(id);

		return new ResponseEntity<>(new Response(true, " check out successfully", setCheckOutResponse), HttpStatus.OK);
	}

	@GetMapping("/admin/present_employee")
	public ResponseEntity<List<Attendence>> numberOfPresentEmployee() {
		List<Attendence> allPresentEmployee = this.attendenceService.getAllPresentEmployee();

		return ResponseEntity.ok(allPresentEmployee);
	}

	@GetMapping("/employee/attendence/month/{emp_id}")
	public ResponseEntity<List<Attendence>> getAllAttendence(@PathVariable("emp_id") int emp_id) throws UserException {
		List<Attendence> allAttendenceOfEmployee = this.attendenceService.getAllAttendenceOfEmployee(emp_id);

		return ResponseEntity.ok(allAttendenceOfEmployee);
	}

	@GetMapping("/admin/attendence/date")
	public ResponseEntity<List<Attendence>> getAttendenceForEmployeeInMonth(@RequestParam LocalDate date,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<Attendence> filteredPresentEmp = attendenceService.getAttendenceFilterByDate(LocalDate.now(), page, size);

		return ResponseEntity.ok(filteredPresentEmp);

	}
}
