package com.employeemanagement.serviceImpl;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employeemanagement.Repository.AdminRepository;
import com.employeemanagement.Request.AdminDto;
import com.employeemanagement.Request.JwtAuthenticationRequest;
import com.employeemanagement.Response.JwtAuthenticationResponse;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Admin;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// finding for admin by Email
	@Override
	public Admin findByEmail(String email) {

		return this.adminRepo.getAdminByName(email);

	}

	@Override
	public Admin getAdminById(int id) throws UserException {

		Optional<Admin> admin = this.adminRepo.findById(id);

		if (admin.isPresent()) {

			return admin.get();
		}

		throw new UserException(false, "User not found with  id :" + id, 404);

	}

	// Creating Admin
	@Override
	public Response createAdmin(AdminDto req) throws UserException, IOException {

		Admin exists = this.findByEmail(req.getEmail());

		if (exists != null) {

			throw new UserException(false, "User already present with  email :" + req.getEmail(), 409);

		}

		String random = UUID.randomUUID().toString().replaceAll("-", "");
		Admin admin = new Admin();
		admin.setFirstName(req.getFirstName());
		admin.setLastName(req.getLastName());
		admin.setEmail(req.getEmail());
		admin.setPassword(passwordEncoder.encode(req.getPassword()));
		admin.setPhone(req.getPhone());
		admin.setGender(req.getGender());
		admin.setVerificationCode(random);
		admin.setRole("ADMIN");
		admin.setEnable(true);
		admin.setActive(false);
		admin.setImage(null);

		Admin savedAdmin = adminRepo.save(admin);

		return new Response(true, "User created successfully", savedAdmin);

	}

	// for Changing Status
	@Override
	public Admin verify(String code) {

		Admin admin = this.adminRepo.findByVerificationCode(code);

		if (admin == null || admin.isEnable()) {

			return null;

		} else {

			adminRepo.enable(admin.getId());
			return admin;
		}

	}

	// Login Admin
	@Override
	public JwtAuthenticationResponse login(JwtAuthenticationRequest authenticationRequest) throws UserException {
		String email = authenticationRequest.getEmail();
		var loadUserByUsername = this.userDetailService.loadUserByUsername(email);
		Admin admin = this.findByEmail(email);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
				authenticationRequest.getPassword()));

		String jwtToken = this.jwtUtils.generateToken(loadUserByUsername, admin.getId(), admin.getRole());

		if (admin.isActive()) {

			throw new UserException(false, "you already have logged in another device", 500);

		}

		admin.setActive(false);

		Admin loggedinAdmin = this.adminRepo.save(admin);

		return new JwtAuthenticationResponse(true, "login successfully", jwtToken, loggedinAdmin);

	}

	@Override
	public Admin updateDetails(int admin_id, AdminDto req) throws UserException {

		Admin admin = this.getAdminById(admin_id);
		admin.setFirstName(req.getFirstName());
		admin.setLastName(req.getLastName());
		admin.setEmail(req.getEmail());
		admin.setPhone(req.getPhone());
		admin.setGender(req.getGender());
		admin.setImage(req.getImage());

		return this.adminRepo.save(admin);

	}

	@Override
	public Response logout(int id) throws UserException {
		Admin admin = this.getAdminById(id);
		if (!admin.isActive()) {

			throw new UserException(false, "you already logged out", 500);
		}
		SecurityContextHolder.clearContext();
		this.adminRepo.changeActiveStatus(admin.getId());
		return new Response(true, "logout successfully", null);

	}

	@Override
	public boolean checkPassword(String rawPassword, String encodedPassword) {

		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	@Override
	public Response changePassword(int id, String oldPassword, String newPassword)
			throws UserException, CustomeException {
		Admin admin = this.getAdminById(id);
		if (this.checkPassword(oldPassword, admin.getPassword())) {

			admin.setPassword(this.passwordEncoder.encode(newPassword));
			return new Response(true, "Password updated successfully");

		}
		throw new CustomeException(false, "Invalid old password", 400);
	}

}

