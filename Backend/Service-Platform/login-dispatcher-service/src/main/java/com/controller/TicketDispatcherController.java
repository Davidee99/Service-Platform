package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.configuration.URLS;
import com.service.RestTemplateService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/dispatcher/")
public class TicketDispatcherController {
	
	@Autowired
	RestTemplateService restTemplateService;
	
	// TICKET SERVICE

		// - User Controller
	
		@PostMapping("ticket-service/ticket/saveTicket/")
		ResponseEntity<?> saveTicket(HttpEntity<?> request) {
			
			String url = URLS.SAVE_TICKET;
			return restTemplateService.sampleRestTemplate(HttpMethod.POST, url, request);
			
		}
		
		@GetMapping("ticket-service/ticket/getUserTickets/")
		ResponseEntity<?> getUserTickets(HttpServletRequest servletRequest){
			String userId;
			try {
				userId = servletRequest.getParameter("userId");
				if(userId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			
			String url = UriComponentsBuilder.fromUriString(URLS.GET_USER_TICKETS).queryParam("userId", userId)
			.build().toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, null);
		}

		@GetMapping("ticket-service/ticket/getWIPTickets/")
		ResponseEntity<?> getWIPTickets(HttpServletRequest servletRequest){
			
			String userId;
			try {
				userId = servletRequest.getParameter("userId");
				if(userId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			String url = UriComponentsBuilder.fromUriString(URLS.GET_WIP_TICKETS).queryParam("userId", userId)
			.build().toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, null);
			
		}

		@GetMapping("ticket-service/ticket/getNonWIPTickets/")
		ResponseEntity<?> getNonWIPTickets(HttpServletRequest servletRequest) {
			
			String userId;
			try {
				userId = servletRequest.getParameter("userId");
				if(userId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			String url = UriComponentsBuilder.fromUriString(URLS.GET_NON_WIP_TICKETS).queryParam("userId", userId)
			.build().toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, null);
			
		}

		// - Operator Controller

		@PutMapping("ticket-service/operator/update-ticket-status/WIP/")
		ResponseEntity<?> updateTicketStatusWIP(HttpEntity<?> request) {
			
			String url = URLS.UPDATE_TICKET_STATUS_WIP;
			return restTemplateService.sampleRestTemplate(HttpMethod.PUT, url, request);
			
		}

		@GetMapping("ticket-service/operator/ticketsWIP/")
		ResponseEntity<?> ticketWIP(HttpServletRequest servletRequest) {
			
			String operatorId;
			try {
				operatorId = servletRequest.getParameter("operatorId");
				if(operatorId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			String url = UriComponentsBuilder.fromUriString(URLS.GET_TICKET_WIP_BY_ID).queryParam("operatorId", operatorId)
					.build().toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, null);
			
		}
		
		@GetMapping("ticket-service/operator/ticketNONWIP/")
		ResponseEntity<?> ticketNONWIP() {
			
			String url = URLS.GET_TICKET_NON_WIP;
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, null);
			
		}

		@PutMapping("ticket-service/operator/close-ticket/")
		ResponseEntity<?> closeTicketOperator(HttpServletRequest servletRequest) {
			String ticketId;
			try {
				ticketId = servletRequest.getParameter("ticketId");
				if(ticketId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			
			String url = UriComponentsBuilder.fromUriString(URLS.CLOSE_TICKET_OPERATOR)
					.queryParam("ticketId", ticketId).build().toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.PUT, url, null);
			
		}

		@PutMapping("ticket-service/operator/change-status-error/")
		ResponseEntity<?> changeStatusErrorTicket(HttpEntity<?> request) {
			
			String url = URLS.CHANGE_STATUS_ERROR;
			return restTemplateService.sampleRestTemplate(HttpMethod.PUT, url, request);
			
		}
		
		// - Admin Controller
		
		@GetMapping("ticket-service/admin/getOpenAndMarkedTickets/")
		ResponseEntity<?> getOpenAndMarkedTickets() {
			
			String url = URLS.GET_OPEN_AND_MARKED_TICKETS;
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, null);
			
		}
		
		@PutMapping("ticket-service/admin/closeTicket/")
		ResponseEntity<?> closeTicketAdmin(HttpServletRequest servletRequest) {
			
			String ticketId;
			try {
				ticketId = servletRequest.getParameter("ticketId");
				if(ticketId.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametri errati o non inseriti");
			}
			
			String url = UriComponentsBuilder.fromUriString(URLS.CLOSE_TICKET_ADMIN)
					.queryParam("ticketId", ticketId).build().toUriString();
			return restTemplateService.sampleRestTemplate(HttpMethod.PUT, url, null);
			
		}
		
		@PutMapping("ticket-service/admin/changeTicketType/")
		ResponseEntity<?> changeTicketType(HttpEntity<?> request) {
			
			String url = URLS.CHANGE_TICKET_TYPE;
			return restTemplateService.sampleRestTemplate(HttpMethod.PUT, url, request);
			
		}
		
		@GetMapping("ticket-service/admin/getTicketsInProgress/")
		ResponseEntity<?> getTicketsInProgress() {
			
			String url = URLS.GET_TICKET_IN_PROGRESS;
			return restTemplateService.sampleRestTemplate(HttpMethod.GET, url, null);
			
		}

}
