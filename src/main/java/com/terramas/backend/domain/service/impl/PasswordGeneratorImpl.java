package com.terramas.backend.domain.service.impl;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.terramas.backend.domain.service.PasswordGenerator;

@Service
public class PasswordGeneratorImpl implements PasswordGenerator {

    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    
    public String generateRandomPassword() {
        String characters = LOWERCASE_LETTERS + UPPERCASE_LETTERS + DIGITS;
        int passwordLength = 8;
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }
}
