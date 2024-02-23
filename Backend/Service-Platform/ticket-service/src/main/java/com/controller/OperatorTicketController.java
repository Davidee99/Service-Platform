package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.dto.ChangeTicketStatusDTO;
import com.model.dto.ChangeTicketStatusErrorDTO;
import com.service.OperatorService;


@RestController
@RequestMapping("/api/ticket-service/operator/")
public class OperatorTicketController {
	
	private final String ACCESS_KEY = "qwerty";

	@Autowired
	private OperatorService operatorService;
	
	@PutMapping("update-ticket-status/WIP/")
	public ResponseEntity<?> updateTicketStatusWIP(@RequestBody ChangeTicketStatusDTO changeStatus,@RequestHeader HttpHeaders requestHeaders ){
		if(requestHeaders.get("access_key") == null || !ACCESS_KEY.equals(requestHeaders.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.updateTicketStatusWIP(changeStatus);
	}
	
	//"http://localhost:8083/api/operator/ticketWIP?operatorId=2"
	@GetMapping("ticketWIP/")
	public ResponseEntity<?> ticketWIP(@RequestParam(name = "operatorId") Long operatorId,@RequestHeader HttpHeaders requestHeadres ) {
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.operatorTickets(operatorId);
	}
	
	//"http://localhost:8083/api/operator/ticketNONWIP?operatorId=2"
	@GetMapping("ticketNONWIP/")
	public ResponseEntity<?> ticketNONWIP(@RequestHeader HttpHeaders requestHeadres ) {
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.ticketsNONWIP();
	}
	
	@PutMapping("close-ticket/")
	public ResponseEntity<?> closeTicket(@RequestParam(name = "ticketId") Long ticketId,@RequestHeader HttpHeaders requestHeadres ){
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.closeTicket(ticketId);
	}
	
	@PutMapping("change-status-error/")
	public ResponseEntity<?> changeStatusErrorTicket(@RequestBody ChangeTicketStatusErrorDTO ticketToChange,@RequestHeader HttpHeaders requestHeadres){
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.changeStatusErrorTicket(ticketToChange);
	}
	
}
