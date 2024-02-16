package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Ticket;
import com.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public Ticket saveTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketRepository.save(ticket);
	}

	@Override
	public Boolean isAccessCodeAlreadyUsed(String result) {
		// TODO Auto-generated method stub

		List<Ticket> queryResult = ticketRepository.findTicketByAccessCode(result);

		return queryResult.size() > 0;
	}

}
