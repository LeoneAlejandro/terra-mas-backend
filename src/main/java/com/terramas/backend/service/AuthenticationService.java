package com.terramas.backend.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.terramas.backend.domain.model.AppUser;
import com.terramas.backend.presentation.AuthenticationRequest;
import com.terramas.backend.presentation.AuthenticationResponse;
import com.terramas.backend.presentation.ChangePasswordRequest;

public interface AuthenticationService {

	public AuthenticationResponse authenticate(AuthenticationRequest request);
	
	public ResponseEntity<String> changePassword(ChangePasswordRequest changePasswordRequest, String email);
	
	public String changeUserRole(String email, String password);

	public Optional<AppUser> fetchUser(String email);
}
