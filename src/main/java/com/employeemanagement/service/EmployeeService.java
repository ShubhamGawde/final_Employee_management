package com.employeemanagement.service;

import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employeemanagement.configuration.UserDetailServicesImpl;
import com.employeemanagement.dao.EmployeeRepository;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.model.JwtAuthenticationRequest;
import com.employeemanagement.model.JwtAuthenticationResponse;

@Transactional
@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UserDetailServicesImpl userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Employee save(Employee employee) {

		return this.employeeRepository.save(employee);

	}

	// Checking employee is exists or not
	public Employee checkEmployee(String email) {
		return employeeRepository.getByEmployeeByEmail(email);
	}

	public Employee getEmployeeById(int id) {
		Employee employee = null;

		employee = this.employeeRepository.findById(id).get();
//		System.out.println(employee);
		return employee;
	}

	// Create Employee
	public JwtAuthenticationResponse createEmployee(Employee emp) {

		emp.setPassword(passwordEncoder.encode(emp.getPassword()));
		emp.setRole("EMPLOYEE");
		emp.setEnabled(false);
		emp.setIs_deleted(false);
		emp.setActive(false);

//		System.out.println(emp);

		String random = UUID.randomUUID().toString().replaceAll("-", "");

		emp.setVerificationCode(random);

		Employee createdEmployee = employeeRepository.saveAndFlush(emp);

		var user = this.userDetailsService.loadUserByUsername(emp.getEmail());

		var jwtToken = this.jwtUtils.generateToken(user);

		return new JwtAuthenticationResponse(jwtToken, createdEmployee);

	}

	public Employee getSingleEmployee(int id) {

		Employee employee = this.employeeRepository.findById(id).orElseThrow();

		return employee;
	}

	// Read Employee
	public List<Employee> getAllEmployee() {

		return this.employeeRepository.getAllEmployee();

	}

	// Delete Employee
	public Employee removeEmployee(Employee employee) {

		this.employeeRepository.delete(employee.getId());
		return employee;

	}

	// Update Employee

	public Employee updateEmployeeRecords(Employee newEmployee, Employee existingEmployee) {

		existingEmployee.setFirstName(newEmployee.getFirstName());
		existingEmployee.setLastName(newEmployee.getLastName());
		existingEmployee.setEmail(newEmployee.getEmail());
		existingEmployee.setPhone(newEmployee.getPhone());
		existingEmployee.setGender(newEmployee.getGender());
		existingEmployee.setMaritalStatus(newEmployee.getMaritalStatus());
		existingEmployee.setPersonalEmail(newEmployee.getPersonalEmail());
		existingEmployee.setAlterPhone(newEmployee.getAlterPhone());
		existingEmployee.setDob(newEmployee.getDob());
		existingEmployee.setPermanentAddr(newEmployee.getPermanentAddr());
		existingEmployee.setCurrentAddr(newEmployee.getCurrentAddr());
		existingEmployee.setJobTitle(newEmployee.getJobTitle());
		existingEmployee.setType(newEmployee.getType());
		existingEmployee.setJoiningDate(newEmployee.getJoiningDate());
		existingEmployee.setWorkLocation(newEmployee.getWorkLocation());
		existingEmployee.setExperience(newEmployee.getExperience());
		existingEmployee.setQualification(newEmployee.getQualification());
		existingEmployee.setStream(newEmployee.getStream());
		existingEmployee.setCollege(newEmployee.getCollege());
		existingEmployee.setUniversity(newEmployee.getUniversity());
		existingEmployee.setProfileImg(newEmployee.getProfileImg());
		existingEmployee.setAdhar(newEmployee.getAdhar());
		existingEmployee.setResult(newEmployee.getResult());
		existingEmployee.setDegree(newEmployee.getDegree());
		Employee updatedEmployee = this.employeeRepository.save(existingEmployee);

		return updatedEmployee;

	}

	public JwtAuthenticationResponse login(JwtAuthenticationRequest authenticationRequest, Employee emp) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
				authenticationRequest.getPassword()));
		String email = authenticationRequest.getEmail();
		var employee = this.userDetailsService.loadUserByUsername(email);
		var jwtToken = this.jwtUtils.generateToken(employee);
		emp.setActive(true);
		Employee savedEmployee = this.employeeRepository.save(emp);

		return new JwtAuthenticationResponse(jwtToken, savedEmployee);
	}

	public void logout(int id) {

		Employee emp = this.employeeRepository.findById(id).orElseThrow();
		if (emp != null) {
			SecurityContextHolder.clearContext();
			System.out.println(SecurityContextHolder.getContext());
			this.employeeRepository.changeActiveStatus(id);
		}

	}

}
