package com.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.model.dto.AuthRequest;

public interface LoginService {

	ResponseEntity<?> login(AuthRequest authRequest, String loginRole);
	
	ResponseEntity<?> cryptoPassword(Map<String,String> password);

}
