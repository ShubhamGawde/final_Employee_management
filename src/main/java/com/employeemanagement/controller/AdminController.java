package com.employeemanagement.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employeemanagement.Request.AdminDto;
import com.employeemanagement.Request.EmployeeDto;
import com.employeemanagement.Request.UpdateEmployeeRequest;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Admin;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.helper.FileUpload;
import com.employeemanagement.service.AdminService;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.serviceImpl.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private FileUpload upload;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${project.path}")
	private String path;

	@Autowired
	private JwtUtils utils;

	// for showing profile

	@GetMapping("/admin/profile")
	public ResponseEntity<Response> getAdmin(Principal principal) throws UserException {
		String email = principal.getName();
		Admin admin = this.adminService.findByEmail(email);

		if (admin != null) {
			return new ResponseEntity<>(new Response(true, "Admin found", admin), HttpStatus.OK);
		}
		throw new UserException(false, "Admin not found with email :" + email, 404);
	}

	@PostMapping("/admin/update_admin/{id}")
	public ResponseEntity<Response> updateAdminRecord(@PathVariable("id") int id, @RequestBody AdminDto req)
			throws UserException {
		Admin updatedDetails = this.adminService.updateDetails(id, req);

		return new ResponseEntity<>(new Response(true, "Updated successfully", updatedDetails), HttpStatus.OK);
	}

	// Employee Creation Handler

	@Validated
	@PostMapping("/admin/add_employee")
	public ResponseEntity<Response> createEmployee(@Valid @RequestBody EmployeeDto req, HttpServletRequest request)
			throws UserException, IOException {
		Response createEmployee = this.employeeService.createEmployee(req);

		return new ResponseEntity<>(createEmployee, HttpStatus.OK);
	}

	// Read single Employee handler

	@GetMapping("/admin/read_employee/{id}")
	public ResponseEntity<Response> displaySingleEmployeeRecord(@PathVariable("id") int id) throws UserException {
		Employee employee = this.employeeService.getEmployeeById(id);
		EmployeeDto mapedEmp = this.modelMapper.map(employee, EmployeeDto.class);

		return new ResponseEntity<>(new Response(true, "Employee details", mapedEmp), HttpStatus.OK);
	}

	// Read All Employee handler

	@GetMapping("/admin/read_employees")
	public ResponseEntity<List<EmployeeDto>> displayAllEmployeesOnAdminPanel() {
		List<EmployeeDto> allEmployee = this.employeeService.getAllEmployee().stream()
				.map(emp -> this.modelMapper.map(emp, EmployeeDto.class)).toList();

		return ResponseEntity.ok(allEmployee);
	}

	// Delete Employee Handler

	@DeleteMapping("/admin/delete_employee/{id}")
	public ResponseEntity<Map<String, Object>> removeEmployeeRecord(@PathVariable("id") int id) throws UserException {
		String msg = this.employeeService.removeEmployee(id);

		return ResponseEntity.ok(Map.of("Success", true, "message", msg));
	}

	// Update Employee Record Handler

	@PostMapping("/admin/update_employee/{id}")
	public ResponseEntity<Response> updateEmployeeRecord(@PathVariable("id") int id,
			@RequestBody UpdateEmployeeRequest req) throws UserException {
		Employee updatedEmployee = this.employeeService.updateEmployeeRecords(id, req);
		EmployeeDto mappedEmployee = this.modelMapper.map(updatedEmployee, EmployeeDto.class);

		return new ResponseEntity<>(new Response(true, "update successfully", mappedEmployee), HttpStatus.OK);
	}

	@PostMapping("admin/upload/excel")
	public ResponseEntity<Map<String, Object>> uploadExcelData(@RequestParam("file") MultipartFile file)
			throws IOException {
		boolean checkFormate = this.employeeService.checkFormate(file);

		if (!checkFormate) {
			return new ResponseEntity<>(
					Map.of("success", false, "message", "file formate is incorrect ", "TimeStamp", LocalDateTime.now()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		this.employeeService.uploadDataUsingExcelSheet(file.getInputStream());

		return new ResponseEntity<>(Map.of("success", true, "Status", 201, "message", "Record inserted "),
				HttpStatus.OK);

	}

	@PutMapping("/admin/logout/{id}")
	public ResponseEntity<Response> logout(HttpServletRequest req, @PathVariable("id") int id) throws UserException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization").substring(7));
		
		if(usrId != id) {
			throw new UserException(false,"user not found with id : " + id, 404);
		}
		Response response = this.adminService.logout(usrId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/admin/get_sorted/employee")
	public ResponseEntity<List<EmployeeDto>> getSortedDate(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "firstName") String firstName,
			@RequestParam(defaultValue = "asc") String direction) {
		List<EmployeeDto> sortedEmployee = this.employeeService.getSortedEmployee(page, size, firstName, direction)
				.stream().map(emp -> modelMapper.map(emp, EmployeeDto.class)).toList();

		return ResponseEntity.ok(sortedEmployee);
	}

	@GetMapping("/admin/demo")
	public String demo() {

		return "message : ok...";
	}

	@PostMapping("/image/upload")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {

		try {
			String uploadImage = this.upload.uploadImage(file);
			return ResponseEntity.ok(uploadImage);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@GetMapping("/image")
	public ResponseEntity<?> getImage(@RequestParam("img") String img) throws MalformedURLException {
		String imagePath = path + File.separator + img;
		Resource resource = new UrlResource(Paths.get(imagePath).toUri());

		if (resource.exists() && resource.isReadable()) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
		} else {

			return ResponseEntity.ok(Map.of("success", false, "Status", 404, "message", "Image not found"));

		}
	}

	@PostMapping("/admin/change/password/{id}")
	public ResponseEntity<Response> changePassword(HttpServletRequest req,@PathVariable("id") int id, @RequestParam String oldPassword,
			@RequestParam String newPassword) throws UserException, CustomeException {
		Integer usrId = utils.getIdFromToken(req.getHeader("Authorization").substring(7));
		Response response = this.adminService.changePassword(usrId, oldPassword, newPassword);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}