package com.employeemanagement.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.employeemanagement.Repository.EmployeeRepo;
import com.employeemanagement.Request.EmployeeDto;
import com.employeemanagement.Request.JwtAuthenticationRequest;
import com.employeemanagement.Request.UpdateEmployeeRequest;
import com.employeemanagement.Response.JwtAuthenticationResponse;
import com.employeemanagement.Response.Response;
import com.employeemanagement.configuration.UserDetailServicesImpl;
import com.employeemanagement.entity.Address;
import com.employeemanagement.entity.AllotedLeave;
import com.employeemanagement.entity.Allowance;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.Salary;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.helper.ExcelUploadHelper;
import com.employeemanagement.helper.FileUpload;
import com.employeemanagement.service.EmployeeService;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepo employeeRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UserDetailServicesImpl userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ExcelUploadHelper excelHelper;

	@Autowired
	private FileUpload fileUpload;

	// Checking employee is exists or not
	@Override
	public Employee findEmployeeByEmail(String email) {

		return employeeRepository.findByEmail(email);
	}

	// Create Employee
	@Override
	public Response createEmployee(EmployeeDto req) throws UserException, IOException {

		Employee isExists = this.employeeRepository.findByEmail(req.getEmail());
		if (isExists != null) {

			throw new UserException(false, "User already present with  email :" + req.getEmail(), 409);
		}

		Employee employee = new Employee();
		Salary salary = new Salary();
		AllotedLeave allotedLeave = new AllotedLeave();
		employee.setFirstName(req.getFirstName());
		employee.setLastName(req.getLastName());
		employee.setEmail(req.getEmail());
		employee.setPassword(passwordEncoder.encode(req.getPassword()));
		employee.setPhone(req.getPhone());
		employee.setAlterPhone(req.getAlterPhone());
		employee.setDob(req.getDob());
		employee.setExperience(req.getExperience());
		employee.setCollege(req.getCollege());
		employee.setGender(req.getGender());
		employee.setMaritalStatus(req.getMaritalStatus());
		employee.setJobTitle(req.getJobTitle());
		employee.setStream(req.getStream());
		employee.setUniversity(req.getUniversity());
		employee.setJoiningDate(req.getJoiningDate());
		employee.setQualification(req.getQualification());
		employee.setType(req.getType());
		employee.setWorkLocation(req.getWorkLocation());
		employee.setAddress(req.getAddress());
		employee.setRole("EMPLOYEE");
		employee.setEnabled(true);
		employee.setDeleted(false);
		employee.setActive(false);
		employee.setProfileImgUrl(req.getProfileImgUrl());

		List<Address> address = employee.getAddress();
		Iterator<Address> iterator = address.iterator();
		while (iterator.hasNext()) {
			iterator.next().setEmployee(employee);
		}
		List<Allowance> allowances = req.getSalary().getAllowances();

		salary.setBasicSalary(req.getSalary().getBasicSalary());
		salary.setDeductions(req.getSalary().getDeductions());
		for (Allowance alr : allowances) {

			Allowance allowance = new Allowance();
			allowance.setAllowance(alr.getAllowance());
			allowance.setAmount(alr.getAmount());
			allowance.setSalary(salary);
			salary.getAllowances().add(allowance);
		}
		employee.setSalary(salary);

		allotedLeave.setDays(req.getLeaveDays());
		allotedLeave.setCategory("PAID");
		allotedLeave.setType("Loss to pay");
		allotedLeave.setEmployee(employee);
		employee.getAllotedLeave().add(allotedLeave);

		Employee createdEmployee = this.employeeRepository.save(employee);

		if (createdEmployee != null) {

			EmployeeDto dto = this.modelMapper.map(createdEmployee, EmployeeDto.class);

			return new Response(true, "User created successfully", dto);
		}

		throw new UserException(false, "User creation failed", 500);

	}

	@Override
	public Employee getEmployeeById(int id) throws UserException {

		Employee employee = this.employeeRepository.findById(id);

		if (employee == null || employee.isDeleted()) {

			throw new UserException(false, "employee not found with  id :" + id, 404);

		}
		return employee;

	}

	// Read Employee
	@Override
	public List<Employee> getAllEmployee() {

		return this.employeeRepository.getAllEmployee();

	}

	// Delete Employee
	@Override
	public String removeEmployee(int empId) throws UserException {

		Employee employee = this.getEmployeeById(empId);
		if (employee != null) {
			employee.setDeleted(true);
			this.employeeRepository.save(employee);
		}
		return "employee deleted successfully";

	}

	// Update Employee
	@Override
	public Employee updateEmployeeRecords(int id, UpdateEmployeeRequest req) throws UserException {

		Employee employee = this.getEmployeeById(id);
		List<Address> address = employee.getAddress();

		employee.setFirstName(req.getFirstName());
		employee.setLastName(req.getLastName());
		employee.setPhone(req.getPhone());
		employee.setGender(req.getGender());
		employee.setMaritalStatus(req.getMaritalStatus());
		employee.setAlterPhone(req.getAlterPhone());
		employee.setDob(req.getDob());
		employee.setJobTitle(req.getJobTitle());
		employee.setType(req.getType());
		employee.setJoiningDate(req.getJoiningDate());
		employee.setWorkLocation(req.getWorkLocation());
		employee.setExperience(req.getExperience());
		employee.setQualification(req.getQualification());
		employee.setStream(req.getStream());
		employee.setCollege(req.getCollege());
		employee.setUniversity(req.getUniversity());
		List<Address> newAddress = req.getAddress();
		for (Address addr : address) {

			for (Address newAddr : newAddress) {

				if (addr.getId() == newAddr.getId()) {

					addr.setCity(newAddr.getCity());
					addr.setCountry(newAddr.getCountry());
					addr.setLandMark(newAddr.getLandMark());
					addr.setPinCode(newAddr.getPinCode());
					addr.setState(newAddr.getState());
					addr.setType(newAddr.getType());
				}

			}
		}
		Employee updatedEmployee = this.employeeRepository.save(employee);

		if (updatedEmployee != null) {

			return updatedEmployee;
		}

		throw new UserException(false, "Updation Failed...", 500);

	}

	@Override
	public JwtAuthenticationResponse login(JwtAuthenticationRequest authenticationRequest) throws UserException {

		String email = authenticationRequest.getEmail();

		Employee employee = this.employeeRepository.findByEmail(email);
		if (employee == null) {
			throw new UserException(false, "invalid email", 404);

		}

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
				authenticationRequest.getPassword()));

		if (employee.isActive()) {

			throw new UserException(false, "you already have logged in another device", 404);
		}

		var emp = this.userDetailsService.loadUserByUsername(email);
		var jwtToken = this.jwtUtils.generateToken(emp, employee.getId(), employee.getRole());
		employee.setActive(true);
		Employee savedEmployee = this.employeeRepository.save(employee);

		EmployeeDto dto = this.modelMapper.map(savedEmployee, EmployeeDto.class);

		return new JwtAuthenticationResponse(true, "Login successfully", jwtToken, dto);
	}

	@Override
	public Response logout(int id) throws UserException {

		Employee emp = this.getEmployeeById(id);

		if (!emp.isActive()) {

			throw new UserException(false, "you already logged out", 404);

		}
		SecurityContextHolder.clearContext();
		this.employeeRepository.changeActiveStatus(emp.getId());
		return new Response(true, "logout successfully", null);

	}

	@Override
	public void uploadDataUsingExcelSheet(InputStream is) throws IOException {

		List<Employee> empList = this.excelHelper.convertExcelToList(is);

		this.employeeRepository.saveAll(empList);

	}

	@Override
	public boolean checkFormate(MultipartFile file) {
		return this.excelHelper.checkExcelFormate(file);

	}

	@Override
	public List<Employee> getSortedEmployee(int page, int size, String firstName, String direction) {

		Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(firstName).ascending()
				: Sort.by(firstName).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Employee> all = this.employeeRepository.findAll(pageable);

		List<Employee> content = all.getContent();

		return content;
	}

	@Override
	public boolean imgUpdateOrDelete(String img, MultipartFile file, int id) throws UserException, IOException {
		Employee emp = this.getEmployeeById(id);
		String imgUrl = null;
		if (!file.isEmpty()) {

			imgUrl = this.fileUpload.uploadImage(file);

		}
		boolean isDeleted = fileUpload.deleteImage(img);
		emp.setProfileImgUrl(imgUrl);
		this.employeeRepository.save(emp);
		return isDeleted;

	}

	public String changePassword(String password, String email) {

		Employee employee = this.findEmployeeByEmail(email);
		employee.setPassword(passwordEncoder.encode(password));
		this.employeeRepository.save(employee);
		return "password update successfully";

	}

}
