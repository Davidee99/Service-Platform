package com.service;

import org.springframework.http.ResponseEntity;

import com.model.dto.AuthRequest;

public interface LoginService {

	ResponseEntity<?> login(AuthRequest authRequest);

}
