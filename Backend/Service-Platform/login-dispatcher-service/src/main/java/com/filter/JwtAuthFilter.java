package com.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.service.security.JwtService;
import com.service.security.LoginInfoDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Definizione filtro personalizzato dove andiamo ad autenticare un utente.
 * L'autenticazione avviene grossomodo controllando che l'header della request
 * contenga un token valido. Se e' valido (e quindi e' associato ad uno user)
 * viene recuperato il relativo loginInfoDetails (un record sul DB che rappresenta le credenziali
 * dell'utente).
 * Dal momento che stiamo eseguendo un filtro all'interno di una catena di filtri, dobbiamo controllare
 * che l'utente non sia stato gia' autenticato da un altro filtro, ovvero che il suo contesto sia vuoto.
 * 
 * Essendo il server stateless, non esiste una sessione che contenga uno stato, quindi il contesto viene
 * ricreato per ogni request. Pertanto controlliamo che sia vuoto solo perche' un altro filtro potrebbe
 * averlo riempito
 * 
 * Passati questi controlli, inserisce i dettagli della request e dello user nel contesto di autenticazione.
 * Questa classe si appoggia ai metodi definiti nel service JwtService.
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	// username = email perche security vuole questo, ciao :)

	@Autowired
	private JwtService jwtService;

	@Autowired
	private LoginInfoDetailsService loginInfoDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
		try {
			String authHeader = request.getHeader("Authorization");
			String token = null;
			String username = null;

			// Controlla che l'utente che si connette abbia un token inserito
			// nell'header della request per estrapolare token e username
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				// TOKEN
				token = authHeader.substring(7);
				// estrazione della email dal token tramite il jwtService

				// Il metodo extractUsername lancia delle eccezioni in caso di token non valido (Andare a
				// vedere metodo extractAllClaims), quindi gestiamo la funzione con il try/catch
				// DA FARE: migliorare questa sezioine di validazione
				try {
					username = jwtService.extractUsername(token);
				} catch (Exception e) {
					System.err.println("Token: " + token + " scaduto");

				}

			}

			// se esiste questo username e non e' gia' stato autenticato da altri filtri...
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				// ...recupera le credenziali dell'utente e...
				// userDetails = wrap di loginInfo
				UserDetails userDetails = loginInfoDetailsService.loadUserByUsername(username);

				// Se ha un token valido, inserisce i dettagli della request e dello user nel contesto di
				// autenticazione
				if (jwtService.validateToken(token, userDetails)) {

					// Creazione di una entita(authToken) che contiene le caratteristiche dello user
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null /* credential */, userDetails.getAuthorities());

					// Vengono impostati nella entita i dettagli della richiesta http(come indirizzo IP,
					// sessione etc..)
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// L'entita viene poi inserira nel contesto di autenticazione, in modo evitare che la
					// request venga rielaborata dal filtro
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

			// infine, continua con la catena di filtri eseguendo il prossimo
			filterChain.doFilter(request, response);
		} catch (UsernameNotFoundException e) {
			System.err.println("Username Not Found Exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
		} catch (ServletException e) {
			System.err.println("ServletException: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.err.println("Unsupported JWT Exception: " + e.getMessage());
		} catch (MalformedJwtException e) {
			System.err.println("JWS Malformed Exception: " + e.getMessage());
		} catch (SignatureException e) {
			System.err.println("JWS Signature Exception: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.err.println("Expired JWT Exception: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.err.println("Illegal Argument Exception: " + e.getMessage());
		}
	}

}
