package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.configuration.URLS;
import com.service.RestTemplateService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/dispatcher/")
public class ChatDispatcherController {
	
	@Autowired
	RestTemplateService restTemplateService;
	
	// CHAT SERVICE

		@PostMapping("chat-service/attachment/sendAttachment/")
		ResponseEntity<?> sendAttachment(HttpEntity<?> request) {
			
			String url = URLS.SEND_ATTACHMENT;
			return restTemplateService.sampleRestTemplate(HttpMethod.POST, url, request);
			
		}

		@GetMapping("chat-service/chat/getChatByTicketId/withAccessCode/")
		ResponseEntity<?> getChatByTicketIdWithAccessCode(HttpServletRequest servletRequest,HttpEntity<?> request) {
			
			String ticketId;
			try {
				ticketId = servletRequest.getParameter("ticketId");
				if(ticketId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			
			String url = UriComponentsBuilder.fromUriString(URLS.GET_CHAT_BY_TICKET_ID_WITH_ACCESS_CODE)
					.queryParam("ticketId", ticketId).build().toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, request);
			
		}
		
		@GetMapping("chat-service/chat/getChatByTicketId/noAccessCode/")
		ResponseEntity<?> getChatByTicketIdNoAccessCode(HttpServletRequest servletRequest) {
			
			String ticketId;
			try {
				servletRequest.getParameter("ticketId");
				ticketId = servletRequest.getParameter("ticketId");
				if(ticketId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			
			String url = UriComponentsBuilder.fromUriString(URLS.GET_CHAT_BY_TICKET_ID_NO_ACCESS_CODE)
					.queryParam("ticketId", ticketId).build().toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, null);
		}

		@GetMapping("chat-service/chat/getChatByChatId/")
		ResponseEntity<?> getChatByChatId(HttpEntity<?> request,HttpServletRequest servletRequest) {
			String chatId;
			try {
				chatId = servletRequest.getParameter("chatId");
				if(chatId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			
			String url = UriComponentsBuilder.fromUriString(URLS.GET_CHAT_BY_CHAT_ID).queryParam("chatId", chatId).build()
					.toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, request);
		}

		@PostMapping("chat-service/message/sendMessage/")
		ResponseEntity<?> sendMessage(HttpEntity<?> request) {
			String url = URLS.SEND_MESSAGE;
			return restTemplateService.sampleRestTemplate(HttpMethod.POST, url, request);
		}

}
