package com.employeemanagement.service;

import java.io.IOException;

import com.employeemanagement.Request.AdminDto;
import com.employeemanagement.Request.JwtAuthenticationRequest;
import com.employeemanagement.Response.JwtAuthenticationResponse;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Admin;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;

public interface AdminService {

	public Admin findByEmail(String email);

	public Response createAdmin(AdminDto req) throws UserException, IOException;

	public JwtAuthenticationResponse login(JwtAuthenticationRequest authenticationRequest) throws UserException;

	public Admin updateDetails(int id, AdminDto req) throws UserException;

	public Admin verify(String code);

	public Response logout(int id) throws UserException;

	public Admin getAdminById(int id) throws UserException;

	public boolean checkPassword(String rawPassword, String encodedPassword);

	public Response changePassword(int id, String oldPassword, String newPassword) throws UserException, CustomeException;

}
