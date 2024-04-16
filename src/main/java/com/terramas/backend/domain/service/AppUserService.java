package com.terramas.backend.domain.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.terramas.backend.domain.model.AppUser;

public interface AppUserService {

	public UserDetails loadUserByUsername(String s);
	
	public String singUpUser(AppUser appUser);
	
}
