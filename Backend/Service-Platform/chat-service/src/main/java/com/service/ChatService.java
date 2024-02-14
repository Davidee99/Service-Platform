package com.service;

import com.model.dto.SendAttachmentDTO;
import com.model.dto.SendMessageDTO;
import com.model.entity.Attachment;
import com.model.entity.Chat;
import com.model.entity.Message;
import com.model.wrapper.ResponseWrapper;

public interface ChatService {

	Chat saveChat(Chat chat);

	Chat getChatByTicketId(Long ticketId);

	ResponseWrapper<Chat> getNewChatWrapper(Long ticketId);

	ResponseWrapper<Message> sendMessage(SendMessageDTO sendMessageDTO);
	
	ResponseWrapper<Chat> getChatByChatId(Long chatId);
	
	ResponseWrapper<Attachment> sendAttachment(SendAttachmentDTO attachment);
}
