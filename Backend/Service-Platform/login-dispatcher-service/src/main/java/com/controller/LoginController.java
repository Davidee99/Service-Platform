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
	// AuthRequest sarebbe il nostro DTO con il quale effettuiamo il login
	// e verifichiamo questi dati con quelli del DATABASE e per fare il token
	
	//Si è deciso di creare due endpoint sia per user che per employee così da separare
	//i due login
	@PostMapping("/login/user")
	ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
		return loginService.login(authRequest, "USER");
	}

	@PostMapping("/login/employee")
	ResponseEntity<?> loginEmployee(@RequestBody AuthRequest authRequest) {
		return loginService.login(authRequest, "");
	}
}
