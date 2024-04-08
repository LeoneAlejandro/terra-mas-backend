package com.terramas.backend.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.terramas.backend.service.EmailSenderService;
import com.terramas.backend.service.PasswordRecoveryService;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

	private final static String EMAIL_TEMPLATE_LOCATION = "templates/email_template.html";
	private final static String CONFRIM_TOKEN_URL = "http://localhost:3000/reset-password/";
	@Autowired
	public EmailSenderService emailSender;
	@Autowired
	public RedisServiceImpl redisService;
	
//	private PasswordRecoveryServiceImpl(EmailSenderService emailSender, RedisTemplate<String, String> redisTemplate) {
//		this.emailSender = emailSender;
//		this.redisTemplate = redisTemplate;
//	}
//	
	
	@Override
	public ResponseEntity<?> sendPasswordRecoveryLink(String email) {
        String uid = generateUID();
        
        redisService.saveUid(uid, email);      
		
        String recoveryLink = CONFRIM_TOKEN_URL + uid;
        System.out.println(recoveryLink);
		emailSender.send(email, buildEmail(email.toString(), recoveryLink));

        return ResponseEntity.ok().build();
	}	
	
	
	public ResponseEntity<?> checkUid(String uid) {
		
		String uidValidation = redisService.findEmailByUid(uid);
		
		if(uidValidation == null) {
			throw new IllegalArgumentException("UID no existe");
		}
		
		System.out.println(uidValidation);
		
		return ResponseEntity.ok().build();
	}
	
    private String generateUID() {
        // Generate a unique identifier here
        return UUID.randomUUID().toString();
    }

    private String loadEmailTemplate() {
        try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(EMAIL_TEMPLATE_LOCATION);
            if (inputStream == null) {
                throw new RuntimeException("Email template not found.");
            }
            String templateContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
//            String templateContent = "ASD";
            return templateContent;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template.", e);
        }
    }
	
	
    private String buildEmail(String name, String link) {
        String template = loadEmailTemplate();
        template = template.replace("{{name}}", name);
        template = template.replace("{{link}}", link);
        return template;
    }
}
