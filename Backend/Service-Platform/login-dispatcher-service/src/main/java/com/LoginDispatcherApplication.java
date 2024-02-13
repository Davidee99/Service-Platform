package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginDispatcherApplication {

	/*
	 * Cronologia degli eventi login:
	 * 
	 * 1.L'unico endPoint accessibile senza Token è: "/api/authentication/login"
	 * 
	 * 2.Una volta fatta la request al servizio di login con email e password, lo user viene
	 * validato tramite l'authenticationManager e in seguito gli viene assegnato un token
	 * valido per 60 min.
	 * 
	 * 3.L'endpoint restituisce una response di tipo LoginResponse: {
	 * 
	 * "firstName": "John",
	 * 
	 * "lastName": "Doe",
	 * 
	 * "role": "ADMIN",
	 * 
	 * "token": "codice token"
	 * 
	 * }
	 * 
	 * Autenticazione utente:
	 * 
	 * 1.La richiesta viene intercettata dalla catena di filtri presente nella configurazione
	 * di Security (SecurityConfig);
	 * 
	 * 2.Nella chain viene controllato il ruolo e la validita' del token tramite il filtro
	 * custom JwtAuthFilter
	 * 
	 * 3.Sempre nella chain vengono definite le regole per l'accesso agli end points
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(LoginDispatcherApplication.class, args);
	}

}
