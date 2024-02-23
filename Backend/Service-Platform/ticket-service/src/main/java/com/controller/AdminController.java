
package com.controller;

import java.util.List;

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

import com.model.Ticket;
import com.model.dto.ChangeTicketTypeDTO;
import com.service.AdminService;

@RestController
@RequestMapping("/api/admin/") // forse da togliere?
public class AdminController {
	
	private final String ACCESS_KEY = "qwerty";

	@Autowired
	AdminService adminService;

	/*
	 * Visualizza tutti i ticket aperti dagli utenti e marchiati dagli operatori
	 */
	@GetMapping("getOpenAndMarkedTickets/")
	public ResponseEntity<?> getOpenAndMarkedTickets(@RequestHeader HttpHeaders requestHeaders ){
		if(requestHeaders.get("access_key") == null || !ACCESS_KEY.equals(requestHeaders.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}

		List<Ticket> body = null;
		try {
			body = adminService.getOpenAndMarkedTickets();

			if (body == null) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun ticket Trovato"); //404

			}

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

		}

		return ResponseEntity.ok().body(body);

	}

	@PutMapping("closeTicket/")
	public ResponseEntity<?> closeTicket(@RequestParam(name = "ticketId") Long ticketId,@RequestHeader HttpHeaders requestHeaders ){
		if(requestHeaders.get("access_key") == null || !ACCESS_KEY.equals(requestHeaders.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		Ticket body = null;
		try {

			body = adminService.closeTickets(ticketId);

			if (body == null) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket Not Found");

			}

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

		}

		return ResponseEntity.ok().body(body);
	}

	@PutMapping("changeTicketType/")
	public ResponseEntity<?> changeTicketType(@RequestBody ChangeTicketTypeDTO infoDTO,@RequestHeader HttpHeaders requestHeaders ){
		if(requestHeaders.get("access_key") == null || !ACCESS_KEY.equals(requestHeaders.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		Ticket body = null;

		try {

			if (infoDTO == null) {

				throw new IllegalArgumentException("Object ChangeTicketRequest is not valid");

			}

			body = adminService.changeTicketType(infoDTO);

			if (body == null) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operazione fallita");

			}

		}catch(IllegalArgumentException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

		}

		return ResponseEntity.ok().body(body);
	}

//	@GetMapping("getTicketsInProgress/")
//	public ResponseEntity<?> getTicketsInProgress(@RequestParam(name = "operatorId") Long operatorId) {
//
//		ResponseEntity<?> result;
//
//		try {
//			result = adminService.getTicketsInProgress(operatorId);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			System.err.println("Illegal argument");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//		}
//		return result;
//	}

	@GetMapping("getTicketsInProgress/")
	public ResponseEntity<?> getTicketsInProgress(@RequestHeader HttpHeaders requestHeaders ){
		if(requestHeaders.get("access_key") == null || !ACCESS_KEY.equals(requestHeaders.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}

		List<Ticket> body = null;

		try {
			body = adminService.getTicketsInProgress();
			if (body == null) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun ticket Trovato");

			}

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

		}

		return ResponseEntity.ok().body(body);
	}

}