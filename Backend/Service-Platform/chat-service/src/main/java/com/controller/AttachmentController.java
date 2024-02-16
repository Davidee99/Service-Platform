package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.dto.SendAttachmentDTO;
import com.model.entity.Attachment;
import com.model.wrapper.ResponseWrapper;
import com.service.AttachmentService;
import com.utility.AppConstants;
import com.utility.UtilityMethods;

@RestController
@RequestMapping("/api/chat-service/attachment/")
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;

	@PostMapping("sendAttachment/")
	private ResponseEntity<?> sendAttachment(@RequestBody SendAttachmentDTO attachment) {

		try {

			ResponseWrapper<Attachment> result = attachmentService.sendAttachment(attachment);

			if (result.getExceptionError() != null) {

				// Se l'errore è valorizzato allora lo ritorno
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getExceptionError());

			} else {

				try {
					// Se non c'è l'errore allora la chat è valorizzata e me la prendo
					if (result.getObject() == null) {

						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERRORE SCONOSCIUTO");

					} else {
						try {

							if (attachment.getRole().equals(AppConstants.ROLE_OPERATOR)) {

								String to = "";
								// to ce lo prendiamo dall'user_id della chat legata al message

								String accessCode = "";
								// accessCode ce lo prendiamo dal ticket legato alla chat legata al message

								String link = "";
								// Link di default + id della chat legata al message

								if (UtilityMethods.sendDefaultMail(to, accessCode, link)
										.getStatusCode() != HttpStatus.OK) {

									System.err
											.println("Invio mail a " + to + " non riuscito. ChatId: [], MessageId: []");

								}
							}
						} catch (Exception ex) {
							// invio della mail fallito
						}

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
