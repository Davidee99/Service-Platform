package com.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.model.dto.ChangeTicketStatusDTO;
import com.model.dto.ChangeTicketStatusErrorDTO;

public interface OperatorService{

	ResponseEntity<?> updateTicketStatusWIP(ChangeTicketStatusDTO changeStatus);
	
	ResponseEntity<?> operatorTickets(String status ,Long operatorId);
	
	ResponseEntity<?> closeTicket(Map<String,Long> tickeToClose);
	
	ResponseEntity<?> changeStatusErrorTicket(ChangeTicketStatusErrorDTO tickeToChange);
	
}
