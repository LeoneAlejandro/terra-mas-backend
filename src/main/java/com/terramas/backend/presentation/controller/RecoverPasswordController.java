package com.terramas.backend.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terramas.backend.service.PasswordRecoveryService;

@RestController
public class RecoverPasswordController {
	
	@Autowired
	private PasswordRecoveryService passwordRecoveryService;
	
	@PostMapping("/resetpassword/{email}")
	public ResponseEntity<?> initiatePasswordRecovery(@PathVariable String email) {
		return passwordRecoveryService.sendPasswordRecoveryLink(email);
	}
	
//	@PostMapping("/resetpassword/{email}")
//	public String initiatePasswordRecovery(@PathVariable String email) {
//		System.out.println("OK" + email);
//		throw new IllegalArgumentException();
//	}
	
}
