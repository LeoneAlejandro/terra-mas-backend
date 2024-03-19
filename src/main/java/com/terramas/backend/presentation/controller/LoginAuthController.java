package com.terramas.backend.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.terramas.backend.configuration.JwtService;
import com.terramas.backend.datasource.repository.AppUserRepository;
import com.terramas.backend.domain.model.AppUser;
import com.terramas.backend.presentation.AuthenticationRequest;
import com.terramas.backend.presentation.AuthenticationResponse;
import com.terramas.backend.presentation.ChangePasswordRequest;
import com.terramas.backend.service.AuthenticationService;

@RestController
public class LoginAuthController {
	
	private AuthenticationService authenticationService;
	
	public LoginAuthController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse>  authenticate(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
	
	@PostMapping("/changepassword/{email}")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, @PathVariable String email) {
		authenticationService.changePassword(request, email);
		return ResponseEntity.ok("Password changed succesfully");
	}
}
