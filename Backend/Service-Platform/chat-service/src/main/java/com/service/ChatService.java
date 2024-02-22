package com.service;

import org.springframework.http.HttpHeaders;

import com.model.entity.Chat;
import com.model.wrapper.ResponseWrapper;

public interface ChatService {

	Chat saveChat(Chat chat);

	Chat findChatByTicketId(Long ticketId);

	ResponseWrapper<Chat> getNewChatByTicketId(Long ticketId);

	ResponseWrapper<Chat> getChatByChatId(Long chatId);

	String[] getEmailAccessCodeAndChatIdByChatId(Long chatId);

	Long findTicketIdByAccessCode(String accessCode);

	Boolean isAccessCodeValid(HttpHeaders requestHeaders, Long ticketId);

}
