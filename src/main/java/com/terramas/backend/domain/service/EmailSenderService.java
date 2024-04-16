package com.terramas.backend.domain.service;

public interface EmailSenderService {
	void send(String to, String email);
}
