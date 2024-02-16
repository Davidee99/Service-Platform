package com.service;

import com.model.Ticket;

public interface TicketService {

	Ticket saveTicket(Ticket ticket);

	Boolean isAccessCodeAlreadyUsed(String result);

}
