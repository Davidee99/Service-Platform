package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.dto.SendMessageDTO;
import com.model.entity.Message;
import com.model.wrapper.ResponseWrapper;
import com.service.ChatService;
import com.service.MessageService;
import com.utility.AppConstants;
import com.utility.UtilityMethods;

@RestController
@RequestMapping("/api/chat-service/message/")
public class MessageController {
	
	private final String ACCESS_KEY = "qwerty";

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ChatService chatService;

	@PostMapping("sendMessage/")
	private ResponseEntity<?> sendMessage(@RequestBody SendMessageDTO messageDTO, @RequestHeader HttpHeaders requestHeadres) {
		
	if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
	}

		try {
			ResponseWrapper<Message> result = messageService.sendMessage(messageDTO);

			if (result.getExceptionError() != null) {

				// Se l'errore è valorizzato allora lo ritorno
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getExceptionError());

			} else {

				try {
					// Se non c'è l'errore allora la chat è valorizzata e me la prendo
					if (result.getObject() == null) {

						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERRORE SCONOSCIUTO");

					} else {

						if (messageDTO.getRole().equals(AppConstants.ROLE_OPERATOR)) {
							
							//Mi recupero i dati che mi serve inviare via mail grazie al chatId
							
							String[] sendMailInfos = chatService.getEmailAccessCodeAndChatIdByChatId(messageDTO.getChatId());

							String to = sendMailInfos[0];
						

							String accessCode = sendMailInfos[1];
					

							String link = "www.myChat.com/?chatId=" + sendMailInfos[2];
							

							if (UtilityMethods.sendDefaultMail(to, accessCode, link).getStatusCode() != HttpStatus.OK) {

								System.err.println("Invio mail a " + to + " non riuscito. ChatId: [], MessageId: []");

							}
						}

						// Controllare la chat:
						// -deve essere un operator
						// Se va tutto bene invio la mail

					}

				} catch (Exception e) {
					// Gestiamo eccezioni strane non previste
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
				}
			}

		} catch (IllegalArgumentException ieEx) {

			System.err.println(ieEx.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error message: " + ieEx.getMessage() + " stack trace: " + ieEx.getStackTrace());
		}

		return ResponseEntity.status(HttpStatus.OK).body("Messaggio inviato correttamente");
	}
}
