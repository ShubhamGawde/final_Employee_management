package com.employeemanagement.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.employeemanagement.Request.EmailRequest;
import com.employeemanagement.entity.Admin;
import com.employeemanagement.entity.Email;
import com.employeemanagement.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

	// Admin Enabling Process Mail
	@Override
	public void sendVerification(Admin admin, String siteURL) throws UnsupportedEncodingException, MessagingException {

		String verifyURL = siteURL + "/verify?code=" + admin.getVerificationCode();
		String mailContent = "<p> Dear Super Admin  </p>";
		mailContent += "<p> Please click the link below to verify admin and give authority for login </p>";
		mailContent += "<p> admin name is :- " + admin.getFirstName() + " " + admin.getLastName() + "</p>";
		mailContent += " <h3><a href =\"" + verifyURL + "\">VERIFY</a></h3>";
		mailContent += "<p>Thank you, <br>Walking Dreamz</p>";

		Email email = new Email();
		email.setFrom(admin.getEmail());
		email.setSubject("Please verify admin registration");
		email.setTo("shubhamwwd@gmail.com");
		email.setSenderName("Walking Dreamz");
		email.setMessage(mailContent);
		this.sendEmail(email);

	}

// Mail to Admin confirming  Verification
	@Override
	public void sendVerifiedMailToAdmin(Admin admin) throws UnsupportedEncodingException, MessagingException {

		String mailContent = "<p> Dear, " + admin.getFirstName() + " " + admin.getLastName() + "</p>";
		mailContent += "<p>your Registration verification done Successfully.</p>";
		mailContent += "<p> Now you are enable for login  </p>";
		mailContent += "<p> So do your login process </p>";
		mailContent += "<p>Thank you, <br>Walking Dreamz</p>";

		Email email = new Email();
		email.setFrom("shubhamwwd@gmail.com");
		email.setMessage(mailContent);
		email.setSubject("Verification info");
		email.setTo(admin.getEmail());
		email.setSenderName("Walking Dreamz");

		this.sendEmail(email);

	}

	@Override
	public void sendOtp(String mail, int otp) throws UnsupportedEncodingException, MessagingException {

		String mailContent = "<div style = 'border: 1px solid black; padding : 20px;'>" + "<h1>" + "OTP " + "<b>" + otp
				+ "</n>" + "</n>" + "</div>";
		Email email = new Email();
		email.setFrom("shubhamgawde288@gmail.com");
		email.setMessage(mailContent);
		email.setSubject("Forget Password");
		email.setTo(mail);

		this.sendEmail(email);

	}

	@Override
	public void sendEmailToUsers(EmailRequest req) throws UnsupportedEncodingException, MessagingException {

		Iterator<String> to = req.getTo().iterator();
		while (to.hasNext()) {
			Email email = new Email();
			email.setFrom(req.getFrom());
			email.setSubject(req.getSubject());
			email.setTo(to.next());
			email.setSenderName("Walking Dreamz");
			this.sendEmail(email);
		}

	}

	@Override
	public void sendEmail(Email email) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(email.getFrom());
		helper.setTo(email.getTo());

		helper.setSubject(email.getSubject());
		helper.setText(email.getMessage(), true);
		emailSender.send(message);

	}

}
