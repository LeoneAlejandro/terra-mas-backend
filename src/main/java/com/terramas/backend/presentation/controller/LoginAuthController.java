package com.terramas.backend.presentation.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terramas.backend.domain.model.AppUser;
import com.terramas.backend.domain.service.AuthenticationService;
import com.terramas.backend.presentation.AuthenticationRequest;
import com.terramas.backend.presentation.AuthenticationResponse;
import com.terramas.backend.presentation.ChangePasswordRequest;

@RestController
public class LoginAuthController {
	
	private AuthenticationService authenticationService;
	
	public LoginAuthController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
	
	@GetMapping("/userinfo/{email}")
	public Optional<AppUser> userInfo(@PathVariable String email) {
		return authenticationService.fetchUser(email);
	}
	
	@PostMapping("/changepassword/{email}")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, @PathVariable String email) {
		return authenticationService.changePassword(request, email);
	}
	
	@PostMapping("/setAdmin/{email}")
	public String setAdmin(@PathVariable String email, @RequestParam String password) {
		return authenticationService.changeUserRole(email, password);
    }
	
//	@PostMapping("/recoverPassword/{email}")
//	public String resetPassword(@PathVariable String email) {
//		return authenticationService.recoverPassword(email);
//	}
}
