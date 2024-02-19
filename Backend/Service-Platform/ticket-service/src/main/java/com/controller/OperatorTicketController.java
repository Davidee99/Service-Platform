package com.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/operator/")
public class OperatorTicketController {
	
	private final String ACCESS_KEY = "qwerty";

	@Autowired
	private OperatorService operatorService;
	
	@PostMapping("update-ticket-status/WIP")
	public ResponseEntity<?> updateTicketStatusWIP(@RequestBody ChangeTicketStatusDTO changeStatus,@RequestHeader HttpHeaders requestHeadres ){
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.updateTicketStatusWIP(changeStatus);
	}
	
	//"http://localhost:8083/api/operator/ticketWIP?operatorId=2"
	@GetMapping("ticketWIP")
	public ResponseEntity<?> ticketWIP(@RequestParam(name = "operatorId") Long operatorId,@RequestHeader HttpHeaders requestHeadres ) {
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.operatorTickets("WIP",operatorId);
	}
	
	//"http://localhost:8083/api/operator/ticketWIP?operatorId=2"
	@GetMapping("ticketNONWIP")
	public ResponseEntity<?> ticketNONWIP(@RequestParam(name = "operatorId") Long operatorId,@RequestHeader HttpHeaders requestHeadres ) {
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.operatorTickets("NON_WIP",operatorId);
	}
	
	@PutMapping("close-ticket")
	public ResponseEntity<?> closeTicket(@RequestBody Map<String,Long> ticketToClose,@RequestHeader HttpHeaders requestHeadres ){
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.closeTicket(ticketToClose);
	}
	
	@PutMapping("change-status-error")
	public ResponseEntity<?> changeStatusErrorTicket(@RequestBody ChangeTicketStatusErrorDTO ticketToChange,@RequestHeader HttpHeaders requestHeadres){
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		return operatorService.changeStatusErrorTicket(ticketToChange);
	}
	
}
