package com.service;

import com.model.entity.Chat;
import com.model.wrapper.ResponseWrapper;

public interface ChatService {

	Chat saveChat(Chat chat);

	Chat findChatByTicketId(Long ticketId);

	ResponseWrapper<Chat> getNewChatByTicketId(Long ticketId);

	ResponseWrapper<Chat> getChatByChatId(Long chatId);
	
	String[] getEmailAccessCodeAndChatIdByChatId(Long chatId);

}
