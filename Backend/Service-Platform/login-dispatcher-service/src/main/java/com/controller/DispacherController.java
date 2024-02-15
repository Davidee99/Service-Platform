package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.configuration.URLS;

@RestController
@RequestMapping("/api/dispacher/")
public class DispacherController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping("mail-service/sendCustom/")
	ResponseEntity<?> sendCustom(HttpEntity<?> request) {
		String url = URLS.SEND_CUSTOM_MAIL; 
		return sampleRestTemplate(HttpMethod.POST,url,request);
	}
	
	@PostMapping("mail-service/sendDefault/")
	ResponseEntity<?> sendDefault(HttpEntity<?> request) {
		String url = URLS.SEND_DEFAULT_MAIL; 
		return sampleRestTemplate(HttpMethod.POST,url,request);
	}
	
	
	
	
	// PRIVATE //
	
	private final String ACCESS_KEY = "querty";
	
	/**
	 *  If it's a GET -> request = null
	 * @param
	 */
	private ResponseEntity<?> sampleRestTemplate(HttpMethod httpMethod,String url,HttpEntity<?> request) {
		
		HttpHeaders headers = new HttpHeaders();
		
		//Utilizziamo questa chiave per evitare l'esposizione dei metodi del nostro servizio.
		//Questo valore verra' controllato in ogni metodo del nostro servizio che passa per il dispatcher, 
		//in caso di una incongruenza riceveremo uno status 401 UNAUTHORIZED 
		headers.add("access_key",ACCESS_KEY);
		
		HttpEntity<?> newEntity = new HttpEntity<>(request.getBody(),headers);
		
		ResponseEntity<?> response;
		try {
			response = restTemplate.exchange(url, httpMethod, newEntity, String.class);
		} catch (RestClientException e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorizzato");
		}

		return response;
	}

}
