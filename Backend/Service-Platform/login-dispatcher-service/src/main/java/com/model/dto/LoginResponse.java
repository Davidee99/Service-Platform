package com.model.dto;

import lombok.Data;

// DTO della risposta da dare al client

@Data
public class LoginResponse {

	private Long id;
	private String firstName;
	private String lastName;
	private String role;
	private String token;
}
