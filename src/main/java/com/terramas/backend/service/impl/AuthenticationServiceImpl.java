package com.terramas.backend.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.terramas.backend.configuration.JwtService;
import com.terramas.backend.datasource.repository.AppUserRepository;
import com.terramas.backend.domain.model.AppUser;
import com.terramas.backend.presentation.AuthenticationRequest;
import com.terramas.backend.presentation.AuthenticationResponse;
import com.terramas.backend.presentation.ChangePasswordRequest;
import com.terramas.backend.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	private final AppUserRepository appUserRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	
	public AuthenticationServiceImpl(AppUserRepository appUserRepository, JwtService jwtService, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepository = appUserRepository;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		var user = appUserRepository.findByEmail(request.email()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return new AuthenticationResponse(user.getFristName(),jwtToken, user.getAppUserRole());
	}

	
	@Override
	public void changePassword(ChangePasswordRequest request, String email) {
		AppUser user = appUserRepository.findByEmail(email)
						.orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
		
        // chequeo password
        if (!bCryptPasswordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        
        // chequeo nueva password
        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        
        user.setPassword(bCryptPasswordEncoder.encode(request.newPassword()));
        
        appUserRepository.save(user);
		
	}	
}
