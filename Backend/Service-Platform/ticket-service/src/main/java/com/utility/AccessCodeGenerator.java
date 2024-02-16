package com.utility;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.service.TicketService;

@Configuration
public class AccessCodeGenerator {

	@Autowired
	private TicketService ticketService;

	public String generaCodice() {

		String result = "";

		try {

			// Caratteri consentiti per il codice alfanumerico
			String caratteriConsentiti = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

			// Lunghezza del codice
			int lunghezzaCodice = 8;

			// Inizializza un oggetto SecureRandom per la generazione casuale
			SecureRandom random = new SecureRandom();

			// Creazione del codice alfanumerico
			StringBuilder codice = new StringBuilder(lunghezzaCodice);
			for (int i = 0; i < lunghezzaCodice; i++) {
				int index = random.nextInt(caratteriConsentiti.length());
				codice.append(caratteriConsentiti.charAt(index));
			}

			result = codice.toString();

		} catch (Exception ex) {

			System.err.println(ex.getMessage());

			return null;
		}

		if (ticketService.isAccessCodeAlreadyUsed(result)) {

			return generaCodice();
		}

		return result;
	}

}
