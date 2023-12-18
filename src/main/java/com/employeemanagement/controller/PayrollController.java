package com.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Request.PayrollRequest;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Payroll;
import com.employeemanagement.entity.Salary;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.PayrollService;
import com.employeemanagement.service.SalaryService;


@RestController
@RequestMapping("/api")
public class PayrollController {

	@Autowired
	private PayrollService payrollService;

	@Autowired
	private SalaryService salaryService;

	@PostMapping("/admin/generate/payroll/{emp_id}")
	public ResponseEntity<Response> generatePayroll(@PathVariable("emp_id") int emp_id, @RequestBody PayrollRequest req)
			throws UserException {

		Payroll calculatePayRoll = this.payrollService.calculatePayRoll(emp_id, req);

		return new ResponseEntity<>(new Response(true, " Payroll created", calculatePayRoll), HttpStatus.CREATED);

	}

	@PostMapping("/admin/update/salary/{emp_id}")
	public ResponseEntity<Response> updateSalary(@PathVariable("emp_id") int emp_id, @RequestBody Salary newSalary)
			throws UserException {
	
		Salary updateSalary = this.salaryService.updateSalary(emp_id, newSalary);

		return new ResponseEntity<>(new Response(true, " Salary update successfully", updateSalary), HttpStatus.OK);

	}

}
