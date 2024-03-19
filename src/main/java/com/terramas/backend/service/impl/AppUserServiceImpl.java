package com.terramas.backend.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.terramas.backend.datasource.repository.AppUserRepository;
import com.terramas.backend.domain.model.AppUser;
import com.terramas.backend.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {
	
	private final static String USER_NOT_FOUND_MSG = "Usuario con email %s no existe";
	
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private AppUserServiceImpl(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepository = appUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser user = appUserRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG + email));
		
		return User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(user.getAppUserRole().toString())
				.build();
	}

	@Override
	public String singUpUser(AppUser appUser) {
		boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		
		if (userExists) {
			//TODO Chequear si el mail existe, y si no se confirmó el token.
			throw new IllegalStateException("Email already taken");
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
		
		appUser.setPassword(encodedPassword);
		appUserRepository.save(appUser);
		
		return null;
	}

}
