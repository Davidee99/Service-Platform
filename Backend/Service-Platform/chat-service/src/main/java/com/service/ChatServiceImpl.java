package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.entity.Chat;
import com.model.wrapper.ChatWrapper;
import com.repository.ChatRepository;
import com.utility.AppConstants;

import jakarta.persistence.NonUniqueResultException;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepo;

	@Override
	public Chat saveChat(Chat chat) {

		return chatRepo.save(chat);
	}

	@Override
	public Chat getChatByTicketId(Long ticketId) {

		return chatRepo.findByTicketId(ticketId).orElse(null);
	}

	/*
	 * Se non è presente la chat (è la prima volta che l'operatore invia un mex a
	 * user) creo una nuova chat Utilizzo una classe Wrapper per racchiudere in caso
	 * positivo una chat in caso negativo un messaggio di errore da poter loggare
	 */
	@Override
	public ChatWrapper getNewChatWrapper(Long ticketId) {

		ChatWrapper result = new ChatWrapper();
		Chat newChat = new Chat();

		// Valori di default
		Long userId = -1L;
		Long operatorId = -1L;
		String ticketStatus = "";

		try {

			/*
			 * Query che dato un ticketId mi restituisce userId, operatorId e status del
			 * ticket
			 */

			List<Object[]> queryResult = chatRepo.getInterlocutorsId(ticketId);

			if (queryResult == null || queryResult.size() <= 0) {

				// Se la mia query non da risultati vuol dire che il ticketId non è valido
				throw new IllegalArgumentException("Ticket ID non valido: " + ticketId);
			}

			// Assegno il risultato della query alle mie variabili
			for (Object[] row : queryResult) {

				userId = (Long) row[0];
				operatorId = (Long) row[1];
				ticketStatus = (String) row[2];

				// Utilizzo break poiché mi aspetto un solo risultato e più risultati sarebbero
				// prova di errori di coerenza nel db
				break;

			}

			/*
			 * Metodo che si occupa di lanciare un eccezione in caso in cui ci siano degli
			 * errori di coerenza nei dati In particolare controllo: se le variabili sono
			 * nulle se ticketStatus è un valore valido se lo stato è WIP
			 * 
			 */
			checkTicket(ticketId, userId, operatorId, ticketStatus);

		} catch (Exception ex) {

			// Catch per gestire le eccezioni varie che posso aver lanciato
			result.setChat(null);
			result.setExceptionError(ex.getMessage());

			return result;
		}

		// Dopo averli controllati, setto i valori sulla mia variabile
		newChat.setTicketId(ticketId);
		newChat.setUserId(userId);
		newChat.setOperatorId(operatorId);
		newChat.setTimestamp(new Timestamp(System.currentTimeMillis()));

		// Salvo a db
		try {

			newChat = chatRepo.save(newChat);

		} catch (Exception ex) {

			// Catch per gestire le eccezioni varie che posso aver lanciato
			result.setChat(null);
			result.setExceptionError(ex.getMessage());

			return result;
		}

		// Setto la nuova chat (con id salvato) dentro il Wrapper
		result.setChat(newChat);

		return result;
	}

	// Metodo che controlla se le Info sono valorizzate correttamente

	private void checkTicket(Long ticketId, Long userId, Long operatorId, String ticketStatus) {

		// Controlla se userId è null o <= 0
		if (userId == null || userId <= 0l) {

			throw new IllegalArgumentException("ID utente non valido: " + userId);

		}

		// Controlla se operatorId è null o <= 0
		if (operatorId == null || operatorId <= 0l) {

			throw new IllegalArgumentException("ID operatore non valido: " + operatorId);

		}

		// Controlla se ticketStatus è null o vuoto
		if (ticketStatus == null || ticketStatus.isEmpty()) {

			throw new IllegalArgumentException("Lo stato del ticket non può essere nullo o vuoto");

		}

		// Controlla se ticketStatus è un valore valido
		if (!AppConstants.TICKET_STATUS_VALUES_LIST.contains(ticketStatus)) {

			throw new IllegalArgumentException("Valore di stato del ticket non valido: " + ticketStatus);

		}

		List<Object[]> ticketInfos = null;

		try {

			/*
			 * Questa query controlla con una where di 3 parametri (ticketId, userId,
			 * operatorId) + ticket.status = 'WIP' e una group by sullo status (gestiamo
			 * dati duplicati con più stati) lo status del ticket
			 */
			ticketInfos = chatRepo.getTicketStatusStrong(ticketId, userId, operatorId);

		} catch (Exception e) {

			/*
			 * se scatta un eccezione qui vuol dire che la query ha restituito più di una
			 * riga: errore nei dati del db (usiamo il ticketId per fare la query, quindi
			 * deve per forza restituirne solo uno)
			 */

			throw new NonUniqueResultException("La query ha restituito più di un risultato: pulire il database.");
		}

		if (ticketInfos == null) {
			/*
			 * se il risultato è null vuol dire che il db non è pulito errore nei dati del
			 * db (usiamo il ticketId per fare la query, quindi deve per forza restituirne
			 * solo uno)
			 */

			throw new NonUniqueResultException("La query ha restituito più di un risultato: pulire il database.");
		}

		if (ticketInfos.size() > 1) {

			// Caso strano ma non bloccante
			System.err.println("La query per le info del ticket ha restituito più righe. TicketId: " + ticketId);

		}

		Long count = -1l;
		String status = "";

		for (Object[] row : ticketInfos) {

			count = (Long) row[0];
			status = (String) row[1];

			// Faccio fare un solo giro, sarebbe strano se ci fossero più risultati
			break;
		}

		if (count != 1l) {
			/*
			 * se la query restituisce un count diverso da 1 vuol dire che il db non è
			 * pulito errore nei dati del db (usiamo il ticketId per fare la query, quindi
			 * deve per forza restituirne solo uno)
			 */

			throw new NonUniqueResultException("La query ha restituito più di un risultato: pulire il database.");

		}

		// Controllo che i dati combacino
		if (!status.equals(ticketStatus)) {

			throw new IllegalArgumentException("Lo stato del ticket non è valido. Status attuale: [" + status + "] ");

		}

		// Controllo che lo status sia effettivamente WIP
		if (!status.equals(AppConstants.TICKET_STATUS_WIP)) {

			throw new IllegalArgumentException("Lo stato del ticket non è valido. Status attuale: [" + status + "] ");

		}
	}

}
