package com.service;

import java.util.List;

import com.model.Ticket;
import com.model.dto.ChangeTicketTypeDTO;

public interface AdminService {
	List<Ticket> getOpenTickets();

	Ticket closeTickets(Long ticketId);

	Ticket changeTicketType(ChangeTicketTypeDTO info);

	List<Ticket> getTicketsInProgress();
//	ResponseEntity<?> getTicketsInProgress(Long operatorId);
}