package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Ticket;
import com.model.dto.TicketToShowDTO;
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

	@Override
	public List<TicketToShowDTO> getWipTickets(Long userId) {

		List<Ticket> result = ticketRepository.getTicketsByStatusAndUserId("WIP", userId);

		return mapTicketToDTO(result);
	}

	@Override
	public List<TicketToShowDTO> getNonWipTickets(Long userId) {

		List<Ticket> result = ticketRepository.getTicketsByStatusAndUserId("NON_WIP", userId);

		return mapTicketToDTO(result);
	}

	private List<TicketToShowDTO> mapTicketToDTO(List<Ticket> tickets) {
		if (tickets == null) {
			return null;
		}

		List<TicketToShowDTO> dtos = new ArrayList<>();
		for (Ticket ticket : tickets) {
			TicketToShowDTO dto = mapSingleTicketToDTO(ticket);
			dtos.add(dto);
		}
		return dtos;
	}

	private TicketToShowDTO mapSingleTicketToDTO(Ticket ticket) {
		if (ticket == null) {
			return null;
		}

		TicketToShowDTO dto = new TicketToShowDTO();
		dto.setType(ticket.getType());
		dto.setMessage(ticket.getMessage());
		dto.setStatus(ticket.getStatus());
		dto.setStatus_error(ticket.getStatusError());
		dto.setPriority(ticket.getPriority());

		return dto;
	}

	@Override
	public List<Ticket> getAllTicketByUserdId(Long userId) {
		return ticketRepository.findAllByUserId(userId);
	}
}
