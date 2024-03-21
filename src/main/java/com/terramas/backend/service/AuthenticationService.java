package com.terramas.backend.service;

import com.terramas.backend.presentation.AuthenticationRequest;
import com.terramas.backend.presentation.AuthenticationResponse;
import com.terramas.backend.presentation.ChangePasswordRequest;

public interface AuthenticationService {

	public AuthenticationResponse authenticate(AuthenticationRequest request);
	
	public void changePassword(ChangePasswordRequest changePasswordRequest, String email);
	
	public String changeUserRole(String email, String password);
}
