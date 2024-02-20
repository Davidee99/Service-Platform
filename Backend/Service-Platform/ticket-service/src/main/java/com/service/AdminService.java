package com.service;

import org.springframework.http.ResponseEntity;

import com.model.dto.ChangeTicketRequest;

public interface AdminService {
	ResponseEntity<?> getOpenAndApprovedTickets();
	ResponseEntity<?> closeTickets(Long ticketId);
	ResponseEntity<?> changeTicketType(ChangeTicketRequest info);
	ResponseEntity<?> getTicketsExceptNonWip(Long operatorId);
}