package com.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.model.dto.ChangeTicketStatusDTO;
import com.model.dto.ChangeTicketStatusErrorDTO;

public interface OperatorService{

	ResponseEntity<?> updateTicketStatusWIP(ChangeTicketStatusDTO changeStatus);
	
	ResponseEntity<?> operatorTickets(Long operatorId);
	
	ResponseEntity<?> ticketsNONWIP();
	
	ResponseEntity<?> closeTicket(Map<String,Long> tickeToClose);
	
	ResponseEntity<?> changeStatusErrorTicket(ChangeTicketStatusErrorDTO tickeToChange);
	
}
