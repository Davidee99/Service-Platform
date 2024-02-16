package com.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.model.dto.SendToMailDTO;

public class UtilityMethods {
	@Autowired
	private static RestTemplate restTemplate;

	public static ResponseEntity<?> sendDefaultMail(String to, String accessCode, String link) {

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
			return ResponseEntity.status(ex.getStatusCode().value()).body(ex.getMessage());

		} catch (Exception ex) {

			// Gestione degli altri tipi di eccezioni
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore durante l'invio della richiesta.");

		}
	}

}
