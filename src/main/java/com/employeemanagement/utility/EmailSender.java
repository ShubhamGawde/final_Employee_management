package com.employeemanagement.utility;

import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.employeemanagement.model.Email;

import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSender {

	@Autowired
	JavaMailSender mailSender;

	public void sendEmail(Email email) throws UnsupportedEncodingException {

		String messages = email.getMessage();
		String subject = email.getSubject();
		String to = email.getTo();
		String from = email.getFrom();
		String senderName = email.getSenderName();
		String replyTo = email.getReplyTo();

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(from, senderName);
			helper.setReplyTo(replyTo);
			helper.setTo(to);

			helper.setSubject(subject);
			helper.setText(messages, true);
			mailSender.send(message);

		} catch (MessagingException e) {

			e.printStackTrace();
		}

	}

}
