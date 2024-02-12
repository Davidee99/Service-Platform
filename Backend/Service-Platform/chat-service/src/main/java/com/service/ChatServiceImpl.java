package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.entity.Chat;
import com.model.wrapper.ChatWrapper;
import com.repository.ChatRepository;

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

//		return chatRepo.findChatByTicketIdOrderByTimestampAsc(ticketId).get(0).orElse(null);

		return chatRepo.findChatByTicketId(ticketId).orElse(null);
	}

	@Override
	public ChatWrapper getNewChatWrapper(Long ticketId) {
		// TODO Auto-generated method stub
		ChatWrapper result = new ChatWrapper();
		Chat newChat = new Chat();
		newChat.setTicketId(ticketId);

		Long userId = -1L;
		Long operatorId = -1L;
		String ticketStatus = "";

		try {
			List<Object[]> queryResult = chatRepo.findUserAndOperatorByTicketId(ticketId);

			userId = (Long) queryResult.get(0)[0];
			operatorId = (Long) queryResult.get(0)[1];
			ticketStatus = (String) queryResult.get(0)[2];

			checkTicket();

		} catch (Exception ex) {

			System.out.println(ex);
			result.setChat(null);
			result.setExceptionError(ex.getMessage());
			return result;
		}

		newChat.setUserId(userId);
		newChat.setOperatorId(operatorId);
		newChat.setTimestamp(new Timestamp(System.currentTimeMillis()));

		newChat = chatRepo.save(newChat);

		result.setChat(newChat);

		return result;
	}

	// Aggiungere controlli su t.user_id, t.operator_id, t.status e lanciare
	// eccezione per gestire
	private void checkTicket() {

	}

}
