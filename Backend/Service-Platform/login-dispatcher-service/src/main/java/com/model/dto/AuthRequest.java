package com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO per fare la POST che serve a fare il login ed ottenere il token
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

	private String email;
	private String password;
}
