package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Ticket;
import com.model.dto.TicketDTO;
import com.service.TicketService;

@RestController
@RequestMapping("/api/ticket-service/ticket/")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@PostMapping("saveTicket/")
	public ResponseEntity<?> saveTicket(@RequestBody TicketDTO ticketDTO) {

		System.out.println("EEEENTRAAA??");
		System.out.println(ticketDTO == null ? "NOOOOOO" : "SI CHE ENTRAA");

		Ticket ticket = new Ticket();

		ticket.setMessage(ticketDTO.getMessage());
		ticket.setUserId(3L);
		ticket.setOperatorId(null);
		ticket.setAccessCode("12345678");
		ticket.setType(ticketDTO.getType());
		ticket.setOrderId(ticketDTO.getOrderId());

		try {

			ticket = ticketService.saveTicket(ticket);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}

		System.out.println(ticket.getId());
		return ResponseEntity.status(HttpStatus.OK).body(ticket);
	}

}
