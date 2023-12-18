package com.employeemanagement.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.employeemanagement.Request.EmployeeDto;
import com.employeemanagement.Request.JwtAuthenticationRequest;
import com.employeemanagement.Request.UpdateEmployeeRequest;
import com.employeemanagement.Response.JwtAuthenticationResponse;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exceptionhandler.UserException;

public interface EmployeeService {

	public Response createEmployee(EmployeeDto req) throws UserException, IOException;

	public Employee getEmployeeById(int id) throws UserException;

	public Employee findEmployeeByEmail(String email);

	public List<Employee> getAllEmployee();

	public String removeEmployee(int emp_id) throws UserException;

	public Employee updateEmployeeRecords(int id, UpdateEmployeeRequest req) throws UserException;

	public JwtAuthenticationResponse login(JwtAuthenticationRequest authenticationRequest) throws UserException;

	public Response logout(int id) throws UserException;

	public void uploadDataUsingExcelSheet(InputStream is) throws IOException;

	public boolean checkFormate(MultipartFile file);

	public List<Employee> getSortedEmployee(int page, int size, String firstName, String direction);

	public boolean imgUpdateOrDelete(String path, MultipartFile file, int id) throws UserException, IOException;
	
	public String changePassword(String password, String email);

}
