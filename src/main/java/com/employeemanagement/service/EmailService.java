package com.employeemanagement.service;

import java.io.UnsupportedEncodingException;

import com.employeemanagement.Request.EmailRequest;
import com.employeemanagement.entity.Admin;
import com.employeemanagement.entity.Email;

import jakarta.mail.MessagingException;

public interface EmailService {

	public void sendVerification(Admin admin, String siteURL)
			throws UnsupportedEncodingException, MessagingException, MessagingException;

	public void sendVerifiedMailToAdmin(Admin admin) throws UnsupportedEncodingException, MessagingException;

	public void sendOtp(String mail, int otp) throws UnsupportedEncodingException, MessagingException;

	public void sendEmail(Email email) throws MessagingException;

	public void sendEmailToUsers(EmailRequest req) throws UnsupportedEncodingException, MessagingException;

}
