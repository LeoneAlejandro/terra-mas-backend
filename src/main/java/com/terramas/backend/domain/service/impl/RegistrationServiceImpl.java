package com.terramas.backend.domain.service.impl;


import org.springframework.stereotype.Service;

import com.terramas.backend.domain.model.AppUser;
import com.terramas.backend.domain.model.AppUser.AppUserRole;
import com.terramas.backend.domain.service.AppUserService;
import com.terramas.backend.domain.service.RegistrationService;
import com.terramas.backend.presentation.RegistrationRequest;


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
