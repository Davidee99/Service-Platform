package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.model.Employee;
import com.model.Ticket;
import com.model.dto.ChangeTicketStatusDTO;
import com.model.dto.ChangeTicketStatusErrorDTO;
import com.repository.EmployeeRepository;
import com.repository.TicketRepository;

@Service
public class OperatorServiceImpl implements OperatorService {
	
	@Autowired
	private TicketRepository ticketRepo;
	
	@Autowired
	private EmployeeRepository employeeRepo;

	/**
	 * Cambio status in WIP 
	 * 
	 * changeStatus contiene l'operator id e il ticket id
	 * 
	 * @param 
	 **/
	@Override
	public ResponseEntity<?> updateTicketStatusWIP(ChangeTicketStatusDTO changeStatus) {
		
		if(changeStatus.getOperatorId() == null || changeStatus.getTicketId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo mancante");
		}
		
		Ticket ticket;
		
		try {
			ticket = ticketRepo.findById(changeStatus.getTicketId()).get();
		}catch (NoSuchElementException e) { //Eccezione in cui il ticket non viene trovato
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket non esistente");
		} catch (IllegalArgumentException e) { 
			System.err.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		if(ticket.getOperatorId() != null || ticket.getStatus().equals("WIP")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket già preso in carico");
		}
		
		ticket.setOperatorId(changeStatus.getOperatorId());
		ticket.setStatus("WIP");
		ticket = ticketRepo.save(ticket);
		
		return ResponseEntity.status(HttpStatus.OK).body(ticket);
	}


	/**
	 * Chiusura del ticket
	 * 
	 * @param
	 * */
	@Override
	public ResponseEntity<?> closeTicket(Map<String, Long> tickeToClose) {
		Ticket ticket;
		
		try {
			ticket = ticketRepo.findById(tickeToClose.get("ticketId")).get();
		}catch (NoSuchElementException e) { //Eccezione in cui il ticket non viene trovato
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket non esistente");
		} catch (IllegalArgumentException e) { 
			System.err.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		if(ticket.getStatus().equals("CLOSE")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket già chiuso");
		}
		
		ticket.setStatus("CLOSE");
		ticket = ticketRepo.save(ticket);
		
		return ResponseEntity.status(HttpStatus.OK).body(ticket);
	}
	
	
	/**
	 * Cambio statusError del Ticket
	 * 
	 * tickeToChange contiene il ticket id e lo status error da inserire
	 * 
	 * @param
	 * */
	@Override
	public ResponseEntity<?> changeStatusErrorTicket(ChangeTicketStatusErrorDTO tickeToChange) {
		
		String status = tickeToChange.getStatus();
		
		if(tickeToChange.getTicketId() == null || status == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo mancante");
		}
		
		status = status.toUpperCase();
		
		if(!statusErrorList.contains(status)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status Error non esistente");
		}
		
		Ticket ticket;
		
		try {
			ticket = ticketRepo.findById(tickeToChange.getTicketId()).get();
		}catch (NoSuchElementException e) { //Eccezione in cui il ticket non viene trovato
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket non esistente");
		} catch (IllegalArgumentException e) { 
			System.err.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		if(ticket.getStatus().equals(status)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket già impostato come " + status);
		}
		
		ticket.setStatus(status);
		ticket = ticketRepo.save(ticket);
		
		return ResponseEntity.status(HttpStatus.OK).body(ticket);
	}
	
	/**
	 * Lista di tutti i ticket WIP o NON_WIP di un operator
	 * */
	@Override
	public ResponseEntity<?> operatorTickets(String status , Long operatorId) {
		
		if(operatorId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo mancante");
		}
		
		Employee employee;
		try {
			employee = employeeRepo.findById(operatorId).get();
		}catch (NoSuchElementException e) { //Eccezione in cui l'operator non esiste
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Operator non esistente");
		} catch (IllegalArgumentException e) { 
			System.err.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		List<Ticket> tickets;
		try {
			tickets = ticketRepo.findAllByStatusAndOperatorId(status,operatorId); //Lo status viene passato in input in base all'endPoint
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(tickets);
	}
	
	//Array di status error da confrontare con l'input dato dall'utente
	private ArrayList<String> statusErrorList = new ArrayList<>(List.of("DUPLICATED", "FAKE", "WRONG_SECTOR"));

}
