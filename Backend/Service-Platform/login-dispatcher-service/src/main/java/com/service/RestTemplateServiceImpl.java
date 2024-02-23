package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateServiceImpl implements RestTemplateService {
	
	@Autowired
	RestTemplate restTemplate;

	/**
	 * If it's a GET -> request = null
	 * 
	 * @param
	 */

	/*
	 * metodo che aggiunge allo user o all'employee la nostra chiave segreta per poi
	 * effettuare tutte le operazioni autorizzate ps nessuno sa che viene effettuata
	 * questa operazione
	 */
	public ResponseEntity<?> sampleRestTemplate(HttpMethod httpMethod, String url, HttpEntity<?> request) {

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<?> newEntity;

		Object body = null;

		if (request != null) {

			// Qui gestiamo la getChat con access_code, ci prendiamo l'header della request
			// e lo ripassiamo al ChatController
			List<String> accessCodes = request.getHeaders().get("ACCESS_CODE");

			if (accessCodes == null) {
				headers.add("ACCESS_CODE", null);
			} else {
				for (String accessCode : accessCodes) {
					headers.add("ACCESS_CODE", accessCode);
				}
			}

			body = request.getBody();
		} else {
			// se la request è null == è una GET -> body è null
		}

		// Utilizziamo questa chiave per evitare l'esposizione dei metodi del nostro
		// servizio.
		// Questo valore verra' controllato in ogni metodo del nostro servizio che passa
		// per il dispatcher,
		// in caso di una incongruenza riceveremo uno status 401 UNAUTHORIZED
		headers.add("access_key", ACCESS_KEY);

		newEntity = new HttpEntity<>(body, headers);

		ResponseEntity<?> response;
		try {
			response = restTemplate.exchange(url, httpMethod, newEntity, String.class);
		} catch (HttpClientErrorException e) {
			// Se la richiesta ha restituito uno stato di errore (es. 4xx)
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		} catch (HttpServerErrorException e) {
			// Se la richiesta ha generato un errore del server (es. 5xx)
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		} catch (RestClientException e) {
			// Gestisci altre eccezioni di RestClientException, se necessario
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server");
		}

		return response;
	}
	
	// PRIVATE //

	private final String ACCESS_KEY = "qwerty";
}
