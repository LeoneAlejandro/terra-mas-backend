package com.terramas.backend.service.impl;


import org.springframework.stereotype.Service;

import com.terramas.backend.domain.model.AppUser;
import com.terramas.backend.domain.model.AppUser.AppUserRole;
import com.terramas.backend.presentation.RegistrationRequest;
import com.terramas.backend.service.AppUserService;
import com.terramas.backend.service.RegistrationService;


@Service
public class RegistrationServiceImpl implements RegistrationService {

	private final AppUserService appUserService;
	
	private RegistrationServiceImpl(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	@Override
	public String register(RegistrationRequest request) {
		boolean isValidEmail = true;
		
		if (!isValidEmail) {
			throw new IllegalStateException("Email no válido");
		}
		
		appUserService.singUpUser(new AppUser(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword(),
				AppUserRole.USER));
		
		return request.getEmail();
	}
	
	

}
