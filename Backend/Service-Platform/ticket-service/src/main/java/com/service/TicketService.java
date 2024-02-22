package com.service;

import java.util.List;

import com.model.Ticket;
import com.model.dto.TicketToShowDTO;

public interface TicketService {

	Ticket saveTicket(Ticket ticket);

	Boolean isAccessCodeAlreadyUsed(String result);

	List<TicketToShowDTO> getWipTickets(Long userId);

	List<TicketToShowDTO> getNonWipTickets(Long userId);

}
