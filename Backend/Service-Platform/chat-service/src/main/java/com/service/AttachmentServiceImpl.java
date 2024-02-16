package com.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.model.dto.SendAttachmentDTO;
import com.model.entity.Attachment;
import com.model.entity.Chat;
import com.model.wrapper.ResponseWrapper;
import com.repository.AttachmentRepository;
import com.repository.ChatRepository;
import com.utility.AppConstants;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private ChatRepository chatRepo;

	@Autowired
	private AttachmentRepository attachmentRepo;

	/**
	 * Invia un allegato in una chat.
	 * 
	 * @param attachment i dati dell'allegato da inviare
	 * @return una ResponseWrapper contenente l'allegato inviato o un messaggio di
	 *         errore
	 */

	@Override
	public ResponseWrapper<Attachment> sendAttachment(SendAttachmentDTO attachment) {

		// Controlli sull'input
		if (attachment == null) {

			throw new IllegalArgumentException("SendAttachmentDTO non può essere null");

		}

		if (attachment.getChatId() == null || attachment.getChatId() <= 0) {

			throw new IllegalArgumentException("ChatId non può essere null o negativo");

		}

		if (attachment.getSenderId() == null || attachment.getSenderId() <= 0) {

			throw new IllegalArgumentException("SenderId non può essere null o negativo");

		}

		if (attachment.getLink() == null) {

			throw new IllegalArgumentException("Il link non può essere null");

		}

		if (attachment.getLink().isEmpty()) {

			throw new IllegalArgumentException("Il link non può essere vuoto");

		}

		if (attachment.getRole() == null) {

			throw new IllegalArgumentException("Role non può essere null");

		}

		if (attachment.getRole().isEmpty()) {

			throw new IllegalArgumentException("Role non può essere vuoto");

		}

		if (!AppConstants.ROLES_LIST.contains(attachment.getRole())) {

			throw new IllegalArgumentException("Role non valido");

		}

		ResponseWrapper<Attachment> result = new ResponseWrapper<>();

		try {
			// Provo a salvare l'entità nel database
			Attachment pic = new Attachment();

			Chat chat = chatRepo.findById(attachment.getChatId()).orElse(null);
			if (chat == null) {

				// Caso impossibile, invio un messaggio e la chat non esiste??
				throw new Exception("Sei un coglione kys");
			}

			pic.setChat(chat);
			pic.setSenderId(attachment.getSenderId());
			pic.setUrl(attachment.getLink());
			pic.setTimestamp(new Timestamp(System.currentTimeMillis()));

			pic = attachmentRepo.save(pic);
			result.setObject(pic);

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
