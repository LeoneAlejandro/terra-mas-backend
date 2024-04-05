package com.terramas.backend.service;

import org.springframework.http.ResponseEntity;

public interface PasswordRecoveryService {

	public ResponseEntity<?> sendPasswordRecoveryLink(String email);
}
