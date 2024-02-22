package com.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.model.entity.Chat;
import com.model.wrapper.ResponseWrapper;
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
	public Chat findChatByTicketId(Long ticketId) {

		return chatRepo.findByTicketId(ticketId).orElse(null);
	}

	/*
	 * Ottiene una nuova chat per un determinato ticket ID. Se la chat non esiste,
	 * crea una nuova istanza di chat e la salva nel repository. Restituisce una
	 * ResponseWrapper contenente la nuova chat o un messaggio di errore.
	 *
	 * @param ticketId l'ID del ticket per il quale ottenere o creare una chat
	 * 
	 * @return una ResponseWrapper contenente la nuova chat o un messaggio di errore
	 */
	@Override
	public ResponseWrapper<Chat> getNewChatByTicketId(Long ticketId) {

		ResponseWrapper<Chat> result = new ResponseWrapper<Chat>();
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
			checkTicketValidity(ticketId, userId, operatorId, ticketStatus);

		} catch (Exception ex) {

			// Catch per gestire le eccezioni varie che posso aver lanciato
			result.setObject(null);
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
			result.setObject(null);
			result.setExceptionError(ex.getMessage());

			return result;
		}

		// Setto la nuova chat (con id salvato) dentro il Wrapper
		result.setObject(newChat);

		return result;
	}

	@Override
	public ResponseWrapper<Chat> getChatByChatId(Long chatId) {
		// CONTROLLI DI VALIDITA'
		if (chatId == null) {
			throw new IllegalArgumentException("ChatID non può essere null");
		}

		ResponseWrapper<Chat> response = new ResponseWrapper<Chat>();
		try {
			Chat fetchedChat = chatRepo.findById(chatId).orElse(null);
			if (fetchedChat == null) {
				// metodo del service è chiamato quando l'utente clicca sul link inviatogli
				// tramite email
				// se non trova l'id non significa che la chat non è mai esistita, ma significa
				// che è stata cancellata, magari da un admin
				throw new IllegalArgumentException("Chat eliminata");
			}
			response.setObject(fetchedChat);
		} catch (DataIntegrityViolationException divEx) {
			response.setObject(null);
			response.setExceptionError(divEx.getMessage());
		} catch (OptimisticLockingFailureException olfEx) {
			response.setObject(null);
			response.setExceptionError(olfEx.getMessage());
		} catch (DataAccessException daEx) {
			response.setObject(null);
			response.setExceptionError(daEx.getMessage());
		} catch (Exception ex) {
			response.setObject(null);
			response.setExceptionError(ex.getMessage());
		}

		return response;
	}

	/*
	 * Controlla la validità delle informazioni del ticket. Lanciando eccezioni se i
	 * dati non sono validi o se ci sono errori di coerenza.
	 *
	 * @param ticketId l'ID del ticket da controllare
	 * 
	 * @param userId l'ID dell'utente associato al ticket
	 * 
	 * @param operatorId l'ID dell'operatore associato al ticket
	 * 
	 * @param ticketStatus lo stato attuale del ticket
	 * 
	 * @throws IllegalArgumentException se i dati del ticket non sono validi
	 * 
	 * @throws NonUniqueResultException se ci sono errori di coerenza nei dati del
	 * ticket
	 */
	private void checkTicketValidity(Long ticketId, Long userId, Long operatorId, String ticketStatus) {

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
			ticketInfos = chatRepo.getTicketStatusWithStrongValidation(ticketId, userId, operatorId);

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

	@Override
	public String[] getEmailAccessCodeAndChatIdByChatId(Long chatId) {

		// Verifica che l'ID della chat non sia nullo o non valido
		if (chatId == null || chatId <= 0) {

			throw new IllegalArgumentException("L'ID della chat non è valido");

		}

		// Ottieni i risultati dalla query
		List<Object[]> results = chatRepo.getEmailAccessCodeAndChatIdByChatId(chatId);

		// Se non ci sono risultati, restituisci null
		if (results == null || results.isEmpty()) {

			return null;

		}

		// Inizializza l'array di risultato
		String[] result = new String[3];

		// Scorri la lista di risultati
		for (Object[] row : results) {

			// Assegna i valori all'array di risultato
			result[0] = Objects.toString(row[0], null); // email
			result[1] = Objects.toString(row[1], null); // access_code
			result[2] = Objects.toString(row[2], null); // chatId

			// Se hai trovato una riga, esci dal ciclo
			break;
		}

		return result;
	}

	@Override
	public Long findTicketIdByAccessCode(String accessCode) {

		try {

			return chatRepo.findTicketIdByAccessCode(accessCode);
		} catch (Exception ex) {

			System.err.println(ex.getMessage());
			return null;
		}

	}

	@Override
	public Boolean isAccessCodeValid(HttpHeaders requestHeaders, Long ticketId) {
		String accessCode = null;

		try {

			accessCode = requestHeaders.get("ACCESS_CODE").get(0);

		} catch (IndexOutOfBoundsException ioubex) {

			return false;

		}

		if (accessCode == null) {

			return false;

		}

		Long verifiedId = null;

		verifiedId = findTicketIdByAccessCode(accessCode);

		if (verifiedId == null) {

			return false; // 401

		}

		if (!verifiedId.equals(ticketId)) {

			return false;

		}

		return true;
	}

}
