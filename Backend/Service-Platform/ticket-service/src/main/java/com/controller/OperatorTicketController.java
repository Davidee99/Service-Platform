package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

	@Autowired
	private OperatorService operatorService;
	
	@PostMapping("updateTicketStatus/WIP")
	public ResponseEntity<?> updateTicketStatusWIP(@RequestBody ChangeTicketStatusDTO changeStatus,@RequestHeader HttpHeaders headers ){
		return operatorService.updateTicketStatusWIP(changeStatus);
	}
	
	//"http://localhost:8083/api/operator/ticketWIP?operatorId=2"
	
	@GetMapping("ticketWIP")
	public ResponseEntity<?> ticketWIP(@RequestParam(name = "operatorId") Long operatorId,@RequestHeader HttpHeaders headers ) {
		return operatorService.operatorTickets("WIP",operatorId);
	}
	
	@GetMapping("ticketNONWIP")
	public ResponseEntity<?> ticketNONWIP(@RequestParam(name = "operatorId") Long operatorId,@RequestHeader HttpHeaders headers ) {
		return operatorService.operatorTickets("NON_WIP",operatorId);
	}
	
	@PutMapping("closeTicket")
	public ResponseEntity<?> closeTicket(@RequestBody Map<String,Long> ticketToClose,@RequestHeader HttpHeaders headers ){
		return operatorService.closeTicket(ticketToClose);
	}
	
	@PutMapping("changeStatusError")
	public ResponseEntity<?> changeStatusErrorTicket(@RequestBody ChangeTicketStatusErrorDTO ticketToChange,@RequestHeader HttpHeaders headers){
		return operatorService.changeStatusErrorTicket(ticketToChange);
	}
	
}
