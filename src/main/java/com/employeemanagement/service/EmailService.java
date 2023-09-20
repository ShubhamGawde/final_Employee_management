package com.employeemanagement.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.entity.Admin;
import com.employeemanagement.model.Email;
import com.employeemanagement.utility.EmailSender;

import jakarta.mail.MessagingException;

@Service
public class EmailService {

	@Autowired
	private Email email;

	@Autowired
	private EmailSender emailSender;

	// Admin Enabling Process Mail
	public void sendVerification(Admin admin, String siteURL) throws UnsupportedEncodingException, MessagingException {

		String verifyURL = siteURL + "/verify?code=" + admin.getVerificationCode();
		String mailContent = "<p> Dear Super Admin  </p>";
		mailContent += "<p> Please click the link below to verify admin and give authority for login </p>";
		mailContent += "<p> admin name is :- " + admin.getFirstName() + " " + admin.getLastName() + "</p>";
		mailContent += " <h3><a href =\"" + verifyURL + "\">VERIFY</a></h3>";
		mailContent += "<p>Thank you, <br>Walking Dreamz</p>";

		this.email.setFrom(admin.getEmail());
		this.email.setMessage(mailContent);
		this.email.setSubject("Please verify admin registration");
		this.email.setTo("shubhamwwd@gmail.com");
		this.email.setSenderName("Walking Dreamz");
		this.email.setReplyTo(admin.getEmail());

		this.emailSender.sendEmail(email);

	}

// Mail to Admin confirming  Verification
	public void sendVerifiedMailToAdmin(Admin admin) throws UnsupportedEncodingException {

		String mailContent = "<p> Dear, " + admin.getFirstName() + " " + admin.getLastName() + "</p>";
		mailContent += "<p>your Registration verification done Successfully.</p>";
		mailContent += "<p> Now you are enable for login  </p>";
		mailContent += "<p> So do your login process </p>";
		mailContent += "<p>Thank you, <br>Walking Dreamz</p>";

		this.email.setFrom("shubhamgawde288@gmail.com");
		this.email.setMessage(mailContent);
		this.email.setSubject("Verification info");
		this.email.setTo("shubhamwwd@gmail.com");
		this.email.setSenderName("Walking Dreamz");

		this.emailSender.sendEmail(email);

	}

	public void sendOtp(String mail, int otp) throws UnsupportedEncodingException {

		String mailContent = "<div style = 'border: 1px solid black; padding : 20px;'>" + "<h1>" + "OTP " + "<b>" + otp
				+ "</n>" + "</n>" + "</div>";

		this.email.setFrom("shubhamgawde288@gmail.com");
		this.email.setMessage(mailContent);
		this.email.setSubject("Forget Password");
		this.email.setTo(mail);
		this.email.setReplyTo("shubhamgawde288@gmail.com");

		this.emailSender.sendEmail(email);

	}

}
