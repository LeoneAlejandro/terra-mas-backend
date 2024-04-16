package com.terramas.backend.domain.service;

import org.springframework.http.ResponseEntity;

import com.terramas.backend.presentation.RecoveryPasswordRequest;

public interface PasswordRecoveryService {

	public ResponseEntity<?> sendPasswordRecoveryLink(String email);
	
	public ResponseEntity<?> checkUid(String uid);
	
	public ResponseEntity<?> resetNewPassword(RecoveryPasswordRequest request);
}
