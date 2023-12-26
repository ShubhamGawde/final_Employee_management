package com.employeemanagement.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Attendence;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.serviceImpl.AttendenceServiceImpl;
import com.employeemanagement.serviceImpl.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AttendenceController {

	@Autowired
	private AttendenceServiceImpl attendenceService;
	
	@Autowired
	private JwtUtils utils;

	@GetMapping("/employee/ischeck_in")
	public ResponseEntity<Map<String, Object>> isCheckedIn(HttpServletRequest req) throws UserException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization"));
		Boolean isCheckIn = this.attendenceService.isCheckIn(usrId);

		return ResponseEntity.ok(Map.of("Success", true, "Status",200, "CheckedIN", isCheckIn));

	}

	@PostMapping("/employee/check_in")
	public ResponseEntity<Response> checkIn(HttpServletRequest req) throws UserException, CustomeException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization"));
		Attendence addAttendence = this.attendenceService.setChecking(usrId);

		return new ResponseEntity<>(new Response(true, "Attendence and checkin successfully", addAttendence),
				HttpStatus.OK);
	}

	@PostMapping("/employee/check_out/{id}")
	public ResponseEntity<Response> checkOut(HttpServletRequest req) throws UserException, CustomeException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization"));
		Attendence setCheckOutResponse = this.attendenceService.setCheckOut(usrId);

		return new ResponseEntity<>(new Response(true, " check out successfully", setCheckOutResponse), HttpStatus.OK);
	}

	@GetMapping("/admin/present_employee")
	public ResponseEntity<List<Attendence>> numberOfPresentEmployee() {
		List<Attendence> allPresentEmployee = this.attendenceService.getAllPresentEmployee();

		return ResponseEntity.ok(allPresentEmployee);
	}

	@GetMapping("/employee/attendence/month")
	public ResponseEntity<List<Attendence>> getAllAttendence(HttpServletRequest req) throws UserException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization"));
		List<Attendence> allAttendenceOfEmployee = this.attendenceService.getAllAttendenceOfEmployee(usrId);

		return ResponseEntity.ok(allAttendenceOfEmployee);
	}

	@GetMapping("/admin/attendence/date")
	public ResponseEntity<List<Attendence>> getAttendenceForEmployeeInMonth(@RequestParam LocalDate date,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		List<Attendence> filteredPresentEmp = attendenceService.getAttendenceFilterByDate(LocalDate.now(), page, size);

		return ResponseEntity.ok(filteredPresentEmp);

	}
}
