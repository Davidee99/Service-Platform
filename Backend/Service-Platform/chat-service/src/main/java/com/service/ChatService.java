package com.service;

import com.model.entity.Chat;
import com.model.wrapper.ChatWrapper;

public interface ChatService {

	Chat saveChat(Chat chat);

	Chat getChatByTicketId(Long ticketId);

	ChatWrapper getNewChatWrapper(Long ticketId);

}
