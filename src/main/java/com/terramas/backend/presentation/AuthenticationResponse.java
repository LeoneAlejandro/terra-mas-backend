package com.terramas.backend.presentation;

import com.terramas.backend.domain.model.AppUser.AppUserRole;

public record AuthenticationResponse(String firstName, String token, AppUserRole userRole) {

}
