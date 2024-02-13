package com.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/*
	 * Implementazione di AuthenticationEntryPoint per la gestione custom delle eccezioni
	 * causate dall'autenticatione, quindi: Token scaduto o accesso negato per ruolo o Token
	 * non valido
	 */

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {
		try {
			// Creiamo una risposta con il codice 401 e inseriamo nel body il messaggio si errore
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Token scaduto o Accesso Negato");

		} catch (Exception e) {
			// Questa exception può essere sollevata dall'invio della response
			System.err.println("Errore durante la gestione dell'eccezione di autenticazione: " + e);

		}
	}
}