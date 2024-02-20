
package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Ticket;
import com.model.dto.ChangeTicketTypeDTO;
import com.service.AdminService;

@RestController
@RequestMapping("/api/admin/") // forse da togliere?
public class AdminController {

	@Autowired
	AdminService adminService;

	/*
	 * Visualizza tutti i ticket aperti dagli utenti e marchiati dagli operatori
	 */
	@GetMapping("getOpenTickets/")
	public ResponseEntity<?> getOpenTickets() {

		List<Ticket> body = null;
		try {
			body = adminService.getOpenTickets();

			if (body == null) {

				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nessun ticket Trovato");

			}

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

		}

		return ResponseEntity.ok().body(body);

	}

	@GetMapping("closeTicket/")
	public ResponseEntity<?> closeTicket(@RequestParam(name = "ticketId") Long ticketId) {
		Ticket body = null;
		try {

			body = adminService.closeTickets(ticketId);

			if (body == null) {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Operazione fallita");

			}

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

		}

		return ResponseEntity.ok().body(body);
	}

	@PostMapping("changeTicketType/")
	public ResponseEntity<?> changeTicketType(@RequestBody ChangeTicketTypeDTO infoDTO) {
		Ticket body = null;

		try {

			if (infoDTO == null) {

				throw new IllegalArgumentException("Object ChangeTicketRequest is not valid");

			}

			body = adminService.changeTicketType(infoDTO);

			if (body == null) {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Operazione fallita");

			}

		} catch (Exception ex) {

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
	public ResponseEntity<?> getTicketsInProgress() {

		List<Ticket> body = null;

		try {
			body = adminService.getTicketsInProgress();
			if (body == null) {

				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nessun ticket Trovato");

			}

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());

		}

		return ResponseEntity.ok().body(body);
	}

}