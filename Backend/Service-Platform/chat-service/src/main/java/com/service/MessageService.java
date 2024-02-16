package com.service;

import com.model.dto.SendMessageDTO;
import com.model.entity.Message;
import com.model.wrapper.ResponseWrapper;

public interface MessageService {

	ResponseWrapper<Message> sendMessage(SendMessageDTO sendMessageDTO);
	
}
