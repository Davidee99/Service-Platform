package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.model.Ticket;
import com.model.dto.ChangeTicketRequest;
import com.repository.TicketRepository;
import com.utils.AppConstants;

@Service
public class AdminServiceImpl implements AdminService {
    /*
     * Visualizza tutti i ticket aperti dagli utenti e marchiati dagli operatori
     */
    @Autowired
    TicketRepository repo;
    
    @Autowired
    AppConstants appConstants;

    @Override
    public ResponseEntity<?> getOpenAndApprovedTickets() {
    	System.out.println("entranto nel service impl");
    	
    	List<Ticket> a;
		try {
			a = repo.findByStatusErrorIsNotNull();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
    	
    	return ResponseEntity.status(HttpStatus.OK).body(a);
    }
    
    @Override
    public ResponseEntity<?> closeTickets(Long ticketId)
    {
    	// controlli sull'id prima di fare cose con il db
    	if(ticketId == null) 
    	{
    		throw new IllegalArgumentException("TicketID non valido");
    	}
    	try {
			/*List<Ticket> closedTickets = repo.closeTicket(ticketId);*/
    		
    		Ticket closedTicket = repo.findById(ticketId).orElse(null);
    		if(closedTicket == null) 
    		{
    			throw new IllegalArgumentException("Non esiste nessun ticket nel database con il TicketID fornito");
    		}
    		closedTicket.setStatus("CLOSED");
    		closedTicket = repo.save(closedTicket);
			return ResponseEntity.status(HttpStatus.OK).body(closedTicket);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
    }
    
    public ResponseEntity<?> changeTicketType(ChangeTicketRequest info)
    {
    	// CONTROLLI VALIDITA'
    	if(info == null) 
    	{
    		throw new IllegalArgumentException("Object ChangeTicketRequest is not valid");
    	}
    	Long ticketId = info.getTicketId();
    	String type = info.getType();
    	// controlli sull'id prima di fare cose con il db
    	if(ticketId == null)
    	{
    		throw new IllegalArgumentException("TicketID non valido");
    	}
    	if(type == null || type == "" /*|| !appConstants.ticketStatuses.contains(type)*/) // creare lista in AppConstants con i type
    	{
    		throw new IllegalArgumentException("Type non valido");
    	}
    	// FINE DEI CONTROLLI DI VALIDITA'
    	
    	try {
    		Ticket changedTicket = repo.findById(ticketId).orElse(null);
    		if(changedTicket == null) 
    		{
    			throw new IllegalArgumentException("Non esiste nessun ticket nel database con il TicketID provvisto");
    		}
    		changedTicket.setType(type);
    		changedTicket = repo.save(changedTicket);
			return ResponseEntity.status(HttpStatus.OK).body(changedTicket);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Internal Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
    }

	@Override
	public ResponseEntity<?> getTicketsExceptNonWip(Long operatorId) {
		if (operatorId == null || operatorId <= 0) 
		{
            throw new IllegalArgumentException("operatorId e status non possono essere null");
        }
		List<String> currStatus = new ArrayList<String>();
		currStatus.add("WIP");
        currStatus.add("CLOSED");
        currStatus.add("CANCELLED");
        
        List<Ticket> fetchedTickets = new ArrayList<>();
		currStatus.forEach(
				(String s) ->
				{
					try {
						fetchedTickets.addAll(repo.findByOperatorIdAndStatus(operatorId, s));
					} catch (Exception e) {
						e.printStackTrace();
						throw new IllegalArgumentException("operatorId non esistente nel database");
					}
				});
		return ResponseEntity.status(HttpStatus.OK).body(fetchedTickets);
	}
}

