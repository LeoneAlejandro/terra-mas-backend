package com.terramas.backend.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terramas.backend.presentation.RegistrationRequest;
import com.terramas.backend.service.RegistrationService;

@RestController
@RequestMapping(path = "/registration")
public class RegistrationController {

	private final RegistrationService registrationService;
	
	private RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	@PostMapping
	public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
		try {
			String token = registrationService.register(request);
			return ResponseEntity.ok(token);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
