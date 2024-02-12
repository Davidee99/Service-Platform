package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.model.dto.GetChatDTO;
import com.model.dto.SendToMailDTO;
import com.model.entity.Chat;
import com.model.wrapper.ChatWrapper;
import com.service.ChatService;

import jakarta.persistence.NonUniqueResultException;

@RestController
@RequestMapping("/api/chat-service")
public class ChatController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ChatService chatService;

	private ResponseEntity<?> sendDefaultMail(String to, String accessCode, String link) {
		
		String mailEndpoint = "http://localhost:5000/api/mail-service/sendDefault";
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Controllo che i campi non siano null
		if (to == null || accessCode == null || link == null) {
			return ResponseEntity.badRequest().body("I campi 'to', 'accessCode' e 'link' sono obbligatori.");
		}

		// Controllo che i campi non siano vuoti
		if (to.isEmpty() || accessCode.isEmpty() || link.isEmpty()) {
			
			return ResponseEntity.badRequest().body("I campi 'to', 'accessCode' e 'link' non possono essere vuoti.");
			
		}

		SendToMailDTO sendToMailDTO = new SendToMailDTO();
		sendToMailDTO.setTo(to);
		sendToMailDTO.setAccessCode(accessCode);
		sendToMailDTO.setLink(link);

		HttpEntity<SendToMailDTO> requestEntity = new HttpEntity<>(sendToMailDTO, headers);

		try {
			
			return restTemplate.exchange(mailEndpoint, HttpMethod.POST, requestEntity, String.class);
			
		} catch (HttpStatusCodeException ex) {
			
			// Gestione degli errori HTTP
			return ResponseEntity.status(ex.getStatusCode().value()).body(ex.getResponseBodyAsString());
			
		} catch (Exception ex) {
			
			// Gestione degli altri tipi di eccezioni
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore durante l'invio della richiesta.");
			
		}
	}

//	private ResponseEntity<?> sendDefaultMail2(Long ticketId) {
//		// Recupera l'indirizzo email dell'utente associato al ticket
//		String recipientQuery = "SELECT u.email FROM USER u INNER JOIN TICKET t ON u.id = t.user_id WHERE t.id = ?";
//		String recipient;
//		try {
//			recipient = jdbcTemplate.queryForObject(recipientQuery, new Object[] { ticketId }, String.class);
//		} catch (EmptyResultDataAccessException e) {
//			return ResponseEntity.badRequest().body("Ticket non valido.");
//		}
//
//		// Recupera l'access_code associato al ticket
//		String accessCodeQuery = "SELECT access_code FROM TICKET WHERE id = ?";
//		String accessCode;
//		try {
//			accessCode = jdbcTemplate.queryForObject(accessCodeQuery, new Object[] { ticketId }, String.class);
//		} catch (EmptyResultDataAccessException e) {
//			return ResponseEntity.badRequest().body("Ticket non valido.");
//		}
//
//		// Definisci il link di default per ora
//		String link = "https://example.com/link"; // Modifica questo con il link effettivo
//
//		// Invia la mail utilizzando i dati recuperati
//		ResponseEntity<?> mailResponse = sendMail(recipient, accessCode, link);
//
//		// Controlla se l'invio della mail è andato a buon fine
//		if (mailResponse.getStatusCode() != HttpStatus.OK) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'invio della mail.");
//		}
//
//		return ResponseEntity.ok("Mail inviata con successo a " + recipient);
//	}

	@GetMapping("/getChatByTicket")
	private ResponseEntity<?> getChatByTicket(@RequestParam Long ticketId) {

		
		//Ricaviamo la chat tramite ticketId
		Chat responseBody = chatService.getChatByTicketId(ticketId);

		if (responseBody == null) {
			
			/*
			 * Se non è presente la chat (è la prima volta che l'operatore invia un mex a user)
			 * creo una nuova chat
			 * Utilizzo una classe Wrapper per racchiudere
			 * 	in caso positivo una chat
			 * 	in caso negativo un messaggio di errore da poter loggare
			 */
			ChatWrapper result = chatService.getNewChatWrapper(ticketId);
			
			if (result.getExceptionError() != null) {

				//Se l'errore è valorizzato allora lo ritorno
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getExceptionError());

			} 
			else {

				try
				{
					//Se non c'è l'errore alora la chat è valorizzata e me la prendo
					responseBody = result.getChat();
					
				}
				catch(Exception e) 
				{
					//Gestiamo eccezioni strane non previste
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
				}
			}
		}

		return new ResponseEntity<Chat>(responseBody, HttpStatus.OK);

	}

}
