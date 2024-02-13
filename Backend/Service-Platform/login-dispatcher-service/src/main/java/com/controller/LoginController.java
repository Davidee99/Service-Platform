package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.dto.AuthRequest;
import com.service.LoginService;

@RestController
@RequestMapping("/api/authentication")
public class LoginController {

	@Autowired
	LoginService loginService;

	// URL_CLIENT = "http://localhost:8082/api/authentication/login"

	// password codificata di "Password01!" da inserire sul db (ricordatevi di
	// alterare il datatype del campo PSW a varchar(255) )
	// $2a$10$9PLR2wkeNTf8GNbKspvgsOM2ITYCGbPttNuR3hGB3NJ3oQfi7xf36

	// Significa che su postman dovete inserire "Password01!" nel campo PSW, che lui
	// la codifica in quella robaccia
	@PostMapping("/login")
	ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		return loginService.login(authRequest);
	}
}
