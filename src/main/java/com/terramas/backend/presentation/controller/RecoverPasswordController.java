package com.terramas.backend.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/resetpassword/verify-uid/{uid}")
	public ResponseEntity<?> verifyUid(@PathVariable String uid) {
		return passwordRecoveryService.checkUid(uid);
	}
	
	
}
