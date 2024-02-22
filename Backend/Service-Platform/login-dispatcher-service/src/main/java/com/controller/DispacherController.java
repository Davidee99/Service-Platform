package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.configuration.URLS;

@RestController
@RequestMapping("/api/dispatcher/")
public class DispacherController {

	@Autowired
	RestTemplate restTemplate;

	// MAIL SERVICE

	@PostMapping("mail-service/sendCustom/")
	ResponseEntity<?> sendCustom(HttpEntity<?> request) {
		String url = URLS.SEND_CUSTOM_MAIL;
		return sampleRestTemplate(HttpMethod.POST, url, request);
	}

	@PostMapping("mail-service/sendDefault/")
	ResponseEntity<?> sendDefault(HttpEntity<?> request) {
		String url = URLS.SEND_DEFAULT_MAIL;
		return sampleRestTemplate(HttpMethod.POST, url, request);
	}

	// TICKET SERVICE

	// - User Controller

	@PostMapping("ticket-service/ticket/saveTicket/")
	ResponseEntity<?> saveTicket(HttpEntity<?> request) {
		String url = URLS.SAVE_TICKET;
		return sampleRestTemplate(HttpMethod.POST, url, request);
	}

	@GetMapping("ticket-service/ticket/getWIPTickets/")
	ResponseEntity<?> getWIPTickets(@RequestParam(name = "userId") Long userId) {
		String url = UriComponentsBuilder.fromUriString(URLS.GET_WIP_TICKETS).queryParam("userId", userId)
		.build().toUriString();
		return sampleRestTemplate(HttpMethod.GET, url, null);
	}

	@GetMapping("ticket-service/ticket/getNonWIPTickets/")
	ResponseEntity<?> getNonWIPTickets(@RequestParam(name = "userId") Long userId) {
		String url = UriComponentsBuilder.fromUriString(URLS.GET_NON_WIP_TICKETS).queryParam("userId", userId)
		.build().toUriString();
		return sampleRestTemplate(HttpMethod.GET, url, null);
	}

	// - Operator Controller

	@PostMapping("ticket-service/operator/update-ticket-status/WIP")
	ResponseEntity<?> updateTicketStatusWIP(HttpEntity<?> request) {
		String url = URLS.UPDATE_TICKET_STATUS_WIP;
		return sampleRestTemplate(HttpMethod.POST, url, request);
	}

	@GetMapping("ticket-service/operator/ticketsWIP")
	ResponseEntity<?> ticketWIP(@RequestParam(name = "operatorId") Long operatorId) {
		String url = UriComponentsBuilder.fromUriString(URLS.GET_TICKET_WIP_BY_ID).queryParam("operatorId", operatorId)
				.build().toUriString();
		return sampleRestTemplate(HttpMethod.GET, url, null);
	}

	@GetMapping("ticket-service/operator/ticketNONWIP")
	ResponseEntity<?> ticketNONWIP() {
		String url = URLS.GET_TICKET_NON_WIP;
		return sampleRestTemplate(HttpMethod.GET, url, null);
	}

	@PutMapping("ticket-service/operator/close-ticket")
	ResponseEntity<?> closeTicketOperator(HttpEntity<?> request) {
		String url = URLS.CLOSE_TICKET_OPERATOR;
		return sampleRestTemplate(HttpMethod.PUT, url, request);
	}

	@PutMapping("ticket-service/operator/change-status-error")
	ResponseEntity<?> changeStatusErrorTicket(HttpEntity<?> request) {
		String url = URLS.CHANGE_STATUS_ERROR;
		return sampleRestTemplate(HttpMethod.PUT, url, request);
	}
	
	// - Admin Controller
	
	@GetMapping("ticket-service/admin/getOpenTickets/")
	ResponseEntity<?> getOpenTickets() {
		String url = URLS.GET_OPEN_TICKETS;
		return sampleRestTemplate(HttpMethod.GET, url, null);
	}
	
	@PutMapping("ticket-service/admin/closeTicket/")
	ResponseEntity<?> closeTicketAdmin(HttpEntity<?> request,@RequestParam(name = "ticketId") Long ticketId) {
		String url = UriComponentsBuilder.fromUriString(URLS.CLOSE_TICKET_ADMIN)
				.queryParam("ticketId", ticketId).build().toUriString();
		return sampleRestTemplate(HttpMethod.PUT, url, request);
	}
	
	@PutMapping("ticket-service/admin/changeTicketType/")
	ResponseEntity<?> changeTicketType(HttpEntity<?> request) {
		String url = URLS.CHANGE_TICKET_TYPE;
		return sampleRestTemplate(HttpMethod.PUT, url, request);
	}
	@GetMapping("ticket-service/admin/getOpenTickets/")
	ResponseEntity<?> getTicketsInProgress() {
		String url = URLS.GET_TICKET_IN_PROGRESS;
		return sampleRestTemplate(HttpMethod.GET, url, null);
	}

	
	// CHAT SERVICE

	@PostMapping("chat-service/attachment/sendAttachment/")
	ResponseEntity<?> sendAttachment(HttpEntity<?> request) {
		String url = URLS.SEND_ATTACHMENT;
		return sampleRestTemplate(HttpMethod.POST, url, request);
	}

	@GetMapping("chat-service/chat/getChatByTicketId/withAccessCode/")
	ResponseEntity<?> getChatByTicketIdWithAccessCode(@RequestParam(name = "ticketId") Long ticketId,
			HttpEntity<?> request) {
		String url = UriComponentsBuilder.fromUriString(URLS.GET_CHAT_BY_TICKET_ID_WITH_ACCESS_CODE)
				.queryParam("ticketId", ticketId).build().toUriString();
		return sampleRestTemplate(HttpMethod.GET, url, request);
	}

	@GetMapping("chat-service/chat/getChatByTicketId/noAccessCode/")
	ResponseEntity<?> getChatByTicketIdNoAccessCode(@RequestParam(name = "ticketId") Long ticketId) {
		String url = UriComponentsBuilder.fromUriString(URLS.GET_CHAT_BY_TICKET_ID_NO_ACCESS_CODE)
				.queryParam("ticketId", ticketId).build().toUriString();
		return sampleRestTemplate(HttpMethod.GET, url, null);
	}

	@GetMapping("chat-service/chat/getChatByChatId/")
	ResponseEntity<?> getChatByChatId(@RequestParam(name = "chatId") Long chatId, HttpEntity<?> request) {
		String url = UriComponentsBuilder.fromUriString(URLS.GET_CHAT_BY_CHAT_ID).queryParam("chatId", chatId).build()
				.toUriString();
		return sampleRestTemplate(HttpMethod.GET, url, request);
	}

	@PostMapping("chat-service/message/sendMessage/")
	ResponseEntity<?> sendMessage(HttpEntity<?> request) {
		String url = URLS.SEND_MESSAGE;
		return sampleRestTemplate(HttpMethod.POST, url, request);
	}

	// PRIVATE //

	private final String ACCESS_KEY = "qwerty";

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
	private ResponseEntity<?> sampleRestTemplate(HttpMethod httpMethod, String url, HttpEntity<?> request) {

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

}
