package com.terramas.backend.service.impl;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.terramas.backend.configuration.JwtService;
import com.terramas.backend.datasource.repository.AppUserRepository;
import com.terramas.backend.domain.model.AppUser;
import com.terramas.backend.domain.model.AppUser.AppUserRole;
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
		return new AuthenticationResponse(user.getFirstName(),jwtToken, user.getAppUserRole());
	}

	
	@Override
	public ResponseEntity<String> changePassword(ChangePasswordRequest request, String email) {
		AppUser user = appUserRepository.findByEmail(email)
						.orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
		
        // chequeo password
        if (!bCryptPasswordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        
        // chequeo nueva password
        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new IllegalStateException("Las contraseñas no son correctas");
        }
        
        user.setPassword(bCryptPasswordEncoder.encode(request.newPassword()));
        
        appUserRepository.save(user);
		
        return ResponseEntity.ok("Contraseña cambiada correctamente");
	}	
	
	@Override
	public String changeUserRole(String email, String password) {
		var user = appUserRepository.findByEmail(email).orElseThrow();
		
		if(!password.equals("1234")) {
			throw new IllegalStateException("Contraseña incorrecta");
		}
		
		if(user.getAppUserRole().equals(AppUserRole.ADMIN)) {
			return "Usuario ya es ADMIN";
		} else {
			user.setAppUserRole(AppUserRole.ADMIN);
			appUserRepository.save(user);
			return "Role de usuario cambiado a ADMIN";
		}
		
	}

	@Override
	public Optional<AppUser> fetchUser(String email) {
		return appUserRepository.findByEmail(email);
	}
}
