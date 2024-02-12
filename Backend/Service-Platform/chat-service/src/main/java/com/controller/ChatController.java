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

//	@PostMapping("/manageChat")
//	public ResponseEntity<?> manageChat(@RequestBody ChatInputDTO chatInputDTO) {
//		Long chatId = chatInputDTO.getChatId();
//		String content = chatInputDTO.getContent();
//		Long senderId = chatInputDTO.getSenderId();
//		String role = chatInputDTO.getRole();
//		Long ticketId = chatInputDTO.getTicketId();
//
//		// Controllo dei parametri di input
//		if (chatId == null || content == null || senderId == null || role == null || ticketId == null) {
//			return ResponseEntity.badRequest().body("I parametri di input non possono essere null.");
//		}
//
//		// Verifica se è la prima volta che utente e operatore chattano
//		Chat chat;
//		if (chatId <= 0) {
//			// Inserimento di un nuovo record nella tabella CHAT
//			chat = new Chat();
//			chat.setTicketId(ticketId);
//			chat.setUserId(role.equals("user") ? senderId : null);
//			chat.setOperatorId(role.equals("operator") ? senderId : null);
//			chat.setMessages(new ArrayList<>());
//			chat.setAttachments(new ArrayList<>());
//
//			// chiama servizio ticket passando il ticket id per settarlo a WIP
//
//			// Salva la nuova chat nel database
//			chat = chatService.saveChat(chat);
//		} else {
//			// Se non è la prima volta, ottieni l'entità Chat dal database
//			chat = chatService.getChatById(chatId);
//
//			// Controllo se l'utente è autorizzato a modificare la chat
//			if (!role.equals("user") && !role.equals("operator")) {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ruolo non autorizzato.");
//			}
//
//			// Controllo se l'utente è autorizzato ad aggiungere un messaggio o un allegato
//			if ((role.equals("user") && chat.getUserId() != senderId)
//					|| (role.equals("operator") && chat.getOperatorId() != senderId)) {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
//			}
//		}
//
//		// Aggiungi il messaggio o l'allegato alla chat
//		if (content != null && !content.isEmpty()) {
//			Message message = new Message();
//			message.setChat(chat);
//			message.setSenderId(senderId);
//			message.setContent(content);
//			message.setTimestamp(new Timestamp(System.currentTimeMillis()));
//
//			chat.getMessages().add(message);
//		}
//
//		// Aggiungi gli allegati alla chat
//		List<Attachment> attachments = chatInputDTO.getAttachments();
//		if (attachments != null) {
//			for (Attachment attachment : attachments) {
//				attachment.setChat(chat);
//				attachment.setSenderId(senderId);
//				attachment.setTimestamp(new Timestamp(System.currentTimeMillis()));
//			}
//
//			chat.getAttachments().addAll(attachments);
//		}
//
//		// Salva le modifiche nel database
//		chat = chatService.saveChat(chat);
//
//		// Invia la mail
//		ResponseEntity<?> mailResponse = sendDefaultMail("destinatario", "accesso", "link");
//
//		// Controllo se l'invio della mail è andato a buon fine
//		if (mailResponse.getStatusCode() != HttpStatus.OK) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'invio della mail.");
//		}
//
//		// Restituisci la chat aggiornata
//		return ResponseEntity.ok(chat);
//	}

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

	@GetMapping("/getChat")
	private ResponseEntity<?> getChat(@RequestBody GetChatDTO getChatDTO) {

		Chat responseBody;

		System.out.println("Inizio: " + getChatDTO);

		responseBody = chatService.getChatByTicketId(getChatDTO.getTicketId());

		System.out.println("ResponseBody");
		System.out.println(responseBody);

		if (responseBody == null) {

			ChatWrapper result = chatService.getNewChatWrapper(getChatDTO.getTicketId());

			System.out.println("Getnew chat");
			System.out.println(result);
			if (result.getExceptionError() != null) {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getExceptionError());

			} else {
				// try catch
				try
				{
					responseBody = result.getChat();
				}
				catch(Exception e) 
				{
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
				}
			}
		}

		return new ResponseEntity<Chat>(responseBody, HttpStatus.OK);

	}

}
