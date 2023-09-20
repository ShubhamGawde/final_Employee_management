package com.employeemanagement.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employeemanagement.dao.AdminRepository;
import com.employeemanagement.entity.Admin;
import com.employeemanagement.model.JwtAuthenticationRequest;
import com.employeemanagement.model.JwtAuthenticationResponse;

@Service
@Transactional
public class AdminService {

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
	public Admin findByEmail(String email) {

		return this.adminRepo.getAdminByName(email);

	}

	// Creating Admin
	public JwtAuthenticationResponse createAdmin(Admin admin) {

		String random = UUID.randomUUID().toString().replaceAll("-", "");
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		admin.setVerificationCode(random);
		admin.setRole("Admin");
		admin.setEnable(false);
		admin.setActive(false);

		Admin createdAdmin = adminRepo.saveAndFlush(admin);

		var user = this.userDetailService.loadUserByUsername(admin.getEmail());

		var jwtToken = this.jwtUtils.generateToken(user);

		return new JwtAuthenticationResponse(jwtToken, createdAdmin);

	}

	// for Changing Status
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
	public JwtAuthenticationResponse login(JwtAuthenticationRequest authenticationRequest, Admin admin) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
				authenticationRequest.getPassword()));

		String email = authenticationRequest.getEmail();

		var loadUserByUsername = this.userDetailService.loadUserByUsername(email);

		var jwtToken = this.jwtUtils.generateToken(loadUserByUsername);

		admin.setActive(true);
		Admin savedAdmin = this.adminRepo.save(admin);

		return new JwtAuthenticationResponse(jwtToken, savedAdmin);
	}

	public Admin getAdmin(int id) {

		return this.adminRepo.findById(id).orElseThrow();

	}

	public Admin updateDetails(Admin newAdmin, Admin existingAdmin) {

		existingAdmin.setFirstName(newAdmin.getFirstName());
		existingAdmin.setLastName(newAdmin.getLastName());
		existingAdmin.setEmail(newAdmin.getEmail());
		existingAdmin.setPhone(newAdmin.getPhone());
		existingAdmin.setGender(newAdmin.getGender());
		existingAdmin.setImage(newAdmin.getImage());
		existingAdmin.setLinkedin(newAdmin.getLinkedin());
		return this.adminRepo.save(existingAdmin);
	}

	public void logout(int id) {
		Admin admin = this.adminRepo.findById(id).orElseThrow();
//		System.out.println(admin);
		if (admin != null) {
			SecurityContextHolder.clearContext();
			System.out.println(SecurityContextHolder.getContext());
			this.adminRepo.changeActiveStatus(id);

		}

	}

}
