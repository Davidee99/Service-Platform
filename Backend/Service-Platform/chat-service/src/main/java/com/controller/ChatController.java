package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.entity.Chat;
import com.model.wrapper.ResponseWrapper;
import com.service.ChatService;

@RestController
@RequestMapping("/api/chat-service/chat/")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@GetMapping("getChatByTicketId/")
	private ResponseEntity<?> getChatByTicketId(@RequestParam Long ticketId) {

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
	private ResponseEntity<?> getChatByChatId(@RequestParam Long chatId) {

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

			return new ResponseEntity<Chat>(responseBody, HttpStatus.OK);
		}

	}

}
