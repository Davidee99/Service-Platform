package com.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Ticket;
import com.model.dto.ChangeTicketTypeDTO;
import com.repository.TicketRepository;
import com.utils.AppConstants;

@Service
public class AdminServiceImpl implements AdminService {
	/*
	 * Visualizza tutti i ticket aperti dagli utenti e marchiati dagli operatori
	 */
	@Autowired
	TicketRepository ticketRepository;

	@Override
	public List<Ticket> getOpenTickets() {

		List<Ticket> result = null;

		try {

			result = ticketRepository.findByStatusErrorIsNotNull();

		} catch (Exception e) {

			System.err.println(e.getMessage());
			return null;

		}

		return result;
	}

	@Override
	public Ticket closeTickets(Long ticketId) {
		// controlli sull'id prima di fare cose con il db
		if (ticketId == null) {

			throw new IllegalArgumentException("TicketID non valido");

		}

		Ticket closedTicket = null;
		try {

			closedTicket = ticketRepository.findById(ticketId).orElse(null);

			if (closedTicket == null) {

				throw new IllegalArgumentException("Non esiste nessun ticket nel database con il TicketID fornito");

			}

			closedTicket.setStatus(AppConstants.TICKET_STATUS_CLOSED);

			closedTicket = ticketRepository.save(closedTicket);

		} catch (IllegalArgumentException iaex) {

			throw new IllegalArgumentException(iaex.getMessage());

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

		return closedTicket;
	}

	public Ticket changeTicketType(ChangeTicketTypeDTO info) {

		Long ticketId = info.getTicketId();
		String type = info.getType();

		// Controlli sull'id prima di fare cose con il db

		if (ticketId == null) {

			throw new IllegalArgumentException("TicketID non valido");

		}

		if (type == null || type == "" || !AppConstants.TICKET_TYPE_LIST.contains(type)) {

			throw new IllegalArgumentException("Type non valido");

		}

		// FINE DEI CONTROLLI DI VALIDITA'

		Ticket changedTicket = null;

		try {

			changedTicket = ticketRepository.findById(ticketId).orElse(null);

			if (changedTicket == null) {

				throw new IllegalArgumentException("Non esiste nessun ticket nel database con il TicketID provvisto");

			}

			changedTicket.setType(type);

			changedTicket = ticketRepository.save(changedTicket);

		} catch (IllegalArgumentException iaex) {

			throw new IllegalArgumentException(iaex.getMessage());

		}

		catch (Exception e) {

			System.err.println(e.getMessage());
			return null;

		}

		return changedTicket;
	}

//	@Override
//	public ResponseEntity<?> getTicketsInProgress(Long operatorId) {
//
//		Instant start = Instant.now(); // Timestamp iniziale
//		if (operatorId == null || operatorId <= 0) {
//			throw new IllegalArgumentException("operatorId e status non possono essere null");
//		}
//		List<String> currStatus = new ArrayList<String>();
//		currStatus.add("WIP");
//		currStatus.add("CLOSED");
//		currStatus.add("CANCELLED");
//
//		List<Ticket> fetchedTickets = new ArrayList<>();
//		currStatus.forEach((String s) -> {
//			try {
//				fetchedTickets.addAll(ticketRepository.findByOperatorIdAndStatus(operatorId, s));
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new IllegalArgumentException("operatorId non esistente nel database");
//			}
//		});
//
//		Instant end = Instant.now(); // Timestamp finale
//
//		Duration duration = Duration.between(start, end); // Calcola la durata
//
//		long millis = duration.toMillis();
//
//		System.out.println("Versione giacomo" + millis);
//		return ResponseEntity.status(HttpStatus.OK).body(fetchedTickets);
//	}

	@Override
	public List<Ticket> getTicketsInProgress() {

		Instant start = Instant.now(); // Timestamp iniziale
		List<Ticket> result = null;

		try {

			result = ticketRepository.findByStatusNot(AppConstants.TICKET_STATUS_NON_WIP);

		} catch (Exception ex) {

			System.err.println(ex.getMessage());
			return null;

		}

		Instant end = Instant.now(); // Timestamp finale

		Duration duration = Duration.between(start, end); // Calcola la durata

		long millis = duration.toMillis();

		System.out.println("Versione giacomo" + millis);

		return result;
	}
}
