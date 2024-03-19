package com.terramas.backend.presentation;

public record ChangePasswordRequest(String currentPassword, String newPassword, String confirmationPassword) {

}
