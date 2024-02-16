package com.service;

import java.sql.Timestamp;

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

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private ChatRepository chatRepo;

	@Autowired
	private MessageRepository msgRepo;

	/**
	 * Invia un messaggio in una chat.
	 * 
	 * @param sendMessageDTO i dati del messaggio da inviare
	 * @return una ResponseWrapper contenente il messaggio inviato o un messaggio di
	 *         errore
	 */

	@Override
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
		ResponseWrapper<Message> result = new ResponseWrapper<>();

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
