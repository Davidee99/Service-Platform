package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.entity.Chat;
import com.model.wrapper.ResponseWrapper;
import com.service.ChatService;

@RestController
@RequestMapping("/api/chat-service/chat/")
public class ChatController {

	private final String ACCESS_KEY = "qwerty";

	@Autowired
	private ChatService chatService;

	@GetMapping("getChatByTicketId/withAccessCode/")
	private ResponseEntity<?> getChatByTicketIdWithAccessCode(@RequestParam(name = "ticketId") Long ticketId,
			@RequestHeader HttpHeaders requestHeaders) {

		if (requestHeaders.get("access_key") == null || !ACCESS_KEY.equals(requestHeaders.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); // 401
		}

		if (!chatService.isAccessCodeValid(requestHeaders, ticketId)) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ACCESS_CODE non valido"); // 401
		}

		// Ricaviamo la chat tramite ticketId
		Chat responseBody = chatService.findChatByTicketId(ticketId);

		if (responseBody == null) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("L'operatore deve ancora chattare, attendere risposta dall'operatore");
		}

		return new ResponseEntity<Chat>(responseBody, HttpStatus.OK);

	}

	@GetMapping("getChatByTicketId/noAccessCode/")
	private ResponseEntity<?> getChatByTicketIdNoAccessCode(@RequestParam(name = "ticketId") Long ticketId,
			@RequestHeader HttpHeaders requestHeaders) {

		if (requestHeaders.get("access_key") == null || !ACCESS_KEY.equals(requestHeaders.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); // 401
		}

		// Ricaviamo la chat tramite ticketId
		Chat responseBody = chatService.findChatByTicketId(ticketId);

		if (responseBody == null) {

			/*
			 * Se non è presente la chat (è la prima volta che l'operatore invia un mex a
			 * user) creo una nuova chat Utilizzo una classe Wrapper per racchiudere in caso
			 * positivo una chat in caso negativo un messaggio di errore da poter loggare
			 */
			ResponseWrapper<Chat> result = chatService.getNewChatByTicketId(ticketId);

			if (result.getExceptionError() != null) {

				// Se l'errore è valorizzato allora lo ritorno
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getExceptionError());

			} else {

				try {
					// Se non c'è l'errore allora la chat è valorizzata e me la prendo
					responseBody = result.getObject();

					if (responseBody == null) {

						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERRORE SCONOSCIUTO");

					}

				} catch (Exception e) {

					// Gestiamo eccezioni strane non previste
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

				}
			}
		}

		return new ResponseEntity<Chat>(responseBody, HttpStatus.OK);

	}

	@GetMapping("getChatByChatId/")
	private ResponseEntity<?> getChatByChatId(@RequestParam(name = "chatId") Long chatId,
			@RequestHeader HttpHeaders requestHeadres) {

		if (requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); // 401
		}

		ResponseWrapper<Chat> result = chatService.getChatByChatId(chatId);
		Chat responseBody = new Chat();

		if (result.getExceptionError() != null) {

			// Se l'errore è valorizzato allora lo ritorno
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getExceptionError());

		} else {

			try {

				// Se non c'è l'errore allora la chat è valorizzata e me la prendo
				responseBody = result.getObject();

				if (responseBody == null) {

					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERRORE SCONOSCIUTO");

				}

			} catch (Exception e) {

				// Gestiamo eccezioni strane non previste
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

			}

			// Controlli su access_code
			// ci prendiamo ticket_id e facciamo controlli sulla tablla ticket
			if (!chatService.isAccessCodeValid(requestHeadres, responseBody.getTicketId())) {

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ACCESS_CODE non valido"); // 401
			}

			return new ResponseEntity<Chat>(responseBody, HttpStatus.OK);
		}

	}

}
