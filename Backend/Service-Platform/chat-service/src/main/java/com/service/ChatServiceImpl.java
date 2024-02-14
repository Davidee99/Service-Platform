package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.model.dto.SendMessageDTO;
import com.model.entity.Chat;
import com.model.entity.Message;
import com.model.wrapper.ResponseWrapper;
import com.repository.ChatRepository;
import com.repository.MessageRepository;
import com.utility.AppConstants;

import jakarta.persistence.NonUniqueResultException;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepo;

	@Autowired
	private MessageRepository msgRepo;

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
	public ResponseWrapper<Chat> getNewChatWrapper(Long ticketId) {

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
			checkTicket(ticketId, userId, operatorId, ticketStatus);

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

	@Override
	// ATTENZIONE! NON COMPLETO
	public ResponseWrapper<Message> sendMessage(SendMessageDTO sendMessageDTO) {

		// Controlli sull'input
		if (sendMessageDTO == null) {

			throw new IllegalArgumentException("SendMessageDTO non può essere null");

		}

		if (sendMessageDTO.getChatId() == null || sendMessageDTO.getChatId() <= 0) {

			throw new IllegalArgumentException("ChatId non può essere null o negativo");

		}

		if (sendMessageDTO.getSenderId() == null || sendMessageDTO.getSenderId() <= 0) {

			throw new IllegalArgumentException("SenderId non può essere null o negativo");

		}

		if (sendMessageDTO.getContent() == null) {

			throw new IllegalArgumentException("Content non può essere null");

		}

		if (sendMessageDTO.getContent().isEmpty()) {

			throw new IllegalArgumentException("Content non può essere vuoto");

		}

		if (sendMessageDTO.getRole() == null) {

			throw new IllegalArgumentException("Role non può essere null");

		}

		if (sendMessageDTO.getRole().isEmpty()) {

			throw new IllegalArgumentException("Role non può essere vuoto");

		}

		if (!AppConstants.ROLES_LIST.contains(sendMessageDTO.getRole())) {

			throw new IllegalArgumentException("Role non valido");

		}

		// Provo a salvare l'entità nel database
		Message msg = new Message();
		Chat chat = chatRepo.getReferenceById(sendMessageDTO.getChatId());
		ResponseWrapper<Message> result = new ResponseWrapper<Message>();

		msg.setSenderId(sendMessageDTO.getSenderId());
		msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
		msg.setContent(sendMessageDTO.getContent());
		msg.setChat(chat);

		try {

			msg = msgRepo.save(msg);
			result.setObject(msg);

		} catch (DataIntegrityViolationException divEx) {

			result.setObject(null);
			result.setExceptionError(divEx.getMessage());

		} catch (OptimisticLockingFailureException olfEx) {

			result.setObject(null);
			result.setExceptionError(olfEx.getMessage());

		} catch (DataAccessException daEx) {

			result.setObject(null);
			result.setExceptionError(daEx.getMessage());

		} catch (Exception ex) {

			result.setObject(null);
			result.setExceptionError(ex.getMessage());

		}

		return result;
	}

}
