package com.terramas.backend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.terramas.backend.service.EmailSenderService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {

	private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
	
	private final JavaMailSender mailSender;
	
	private EmailSenderServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	@Async
	public void send(String to, String email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Confirm your email");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.error("failed to send email", e);
			throw new IllegalStateException("failed to send email");
		}
	}
}
