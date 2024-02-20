package com.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Ticket;
import com.model.User;
import com.model.dto.TicketToInsertDTO;
import com.model.dto.TicketToShowDTO;
import com.service.OrderInfoService;
import com.service.TicketService;
import com.service.UserService;
import com.utility.AccessCodeGenerator;

@RestController
@RequestMapping("/api/ticket-service/ticket/")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private AccessCodeGenerator codeGenerator;

	@PostMapping("saveTicket/")
	public ResponseEntity<?> saveTicket(@RequestBody TicketToInsertDTO ticketDTO) {

		Ticket ticket = new Ticket();
		try {

			if (ticketDTO == null) {

				throw new IllegalArgumentException("Front end sei proprio scemo");

			}
			User user = userService.getUserFromTicketDTO(ticketDTO);

			if (user == null) {

				throw new IllegalArgumentException("Nome e Cognome non validi");
			}

			if (!userService.isEmailMatching(user, ticketDTO.getEmail())) {

				throw new IllegalArgumentException("Email non valida");
			}

			if (!orderInfoService.isOrderPresent(ticketDTO.getOrderId())) {

				throw new IllegalArgumentException("Ordine non valido");

			}

			ticket.setMessage(ticketDTO.getMessage());
			ticket.setUserId(user.getId());
			ticket.setOperatorId(null);
			ticket.setAccessCode(codeGenerator.generaCodice());
			ticket.setStatus("NON_WIP");
			ticket.setPriority(1);
			ticket.setCreateDate(new Timestamp(System.currentTimeMillis()));
			ticket.setType(ticketDTO.getType());
			ticket.setOrderId(ticketDTO.getOrderId());

			ticket = ticketService.saveTicket(ticket);

		} catch (IllegalArgumentException iax) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(iax.getMessage());

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

		}

		System.out.println(ticket.getId());
		return ResponseEntity.status(HttpStatus.OK).body(ticket);
	}

	@GetMapping("getWIPTickets/")
	public ResponseEntity<?> getWIPTickets() {

		List<TicketToShowDTO> wipTicketList = ticketService.getWipTickets();

		return ResponseEntity.status(HttpStatus.OK).body(wipTicketList);

	}

	@GetMapping("getNonWIPTickets/")
	public ResponseEntity<?> getNonWIPTickets() {

		List<TicketToShowDTO> wipTicketList = ticketService.getNonWipTickets();

		return ResponseEntity.status(HttpStatus.OK).body(wipTicketList);

	}
}
