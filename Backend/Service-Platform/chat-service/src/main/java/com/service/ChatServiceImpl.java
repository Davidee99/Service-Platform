package com.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.dto.TicketInfoDTO;
import com.model.entity.Chat;
import com.model.wrapper.ChatWrapper;
import com.repository.ChatRepository;
import com.utility.AppConstants;

import jakarta.persistence.NonUniqueResultException;


@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepo;

	@Override
	public Chat saveChat(Chat chat) {

		return chatRepo.save(chat);
	}

	@Override
	public Chat getChatByTicketId(Long ticketId) {
		
		return chatRepo.findByTicketId(ticketId).orElse(null);
	}

	@Override
	public ChatWrapper getNewChatWrapper(Long ticketId) {
		// TODO Auto-generated method stub
		ChatWrapper result = new ChatWrapper();
		Chat newChat = new Chat();
		newChat.setTicketId(ticketId);

		Long userId = -1L;
		Long operatorId = -1L;
		String ticketStatus = "";

		try {
			System.out.println("Entro nel blocco try");
			List<Object[]> queryResult = chatRepo.findUserAndOperatorByTicketId(ticketId);
			queryResult.forEach((x)->{System.out.println(x);});
			if(queryResult == null || queryResult.size() <= 0) 
			{
				throw new IllegalArgumentException("Ticket non valido: "+ticketId);
			}
			
			for(Object[] row : queryResult) 
			{
				System.out.println("Row: " + Arrays.toString(row));
				userId = (Long) row[0];
				operatorId = (Long) row[1];
				ticketStatus = (String) row[2];
				System.out.println("finito il for");
				break;
			}
//			userId = ((Number) queryResult.get(0)[0]).longValue();
//			operatorId = ((Number) queryResult.get(0)[1]).longValue();
//			ticketStatus = (String) queryResult.get(0)[2];

			checkTicket(ticketId, userId, operatorId, ticketStatus);

		} catch (Exception ex) {

			System.out.println(ex);
			result.setChat(null);
			result.setExceptionError(ex.getMessage());
			return result;
		}

		newChat.setUserId(userId);
		newChat.setOperatorId(operatorId);
		newChat.setTimestamp(new Timestamp(System.currentTimeMillis()));

		newChat = chatRepo.save(newChat);

		result.setChat(newChat);

		return result;
	}

	// controlla se sono nulli, minori o uguali a zero, se la stringa è vuota, se ticketStatus è un valore valido
	private void checkTicket(Long ticketId, Long userId, Long operatorId, String ticketStatus) {
		// Controlla se userId è null o <= 0
		if (userId == null || userId <= 0l) {
		    throw new IllegalArgumentException("ID utente non valido: " + userId);
		}

		// Controlla se operatorId è null o <= 0
		if (operatorId == null || operatorId <= 0l) {
		    throw new IllegalArgumentException("ID operatore non valido: " + operatorId);
		}

		// Controlla se ticketStatus è null o vuoto
		if (ticketStatus == null || ticketStatus.isEmpty()) {
		    throw new IllegalArgumentException("Lo stato del ticket non può essere nullo o vuoto");
		}

		// Controlla se ticketStatus è un valore valido
		List<String> acceptedValues = AppConstants.getTicketStatusValuesList();
		if (!acceptedValues.contains(ticketStatus)) {
		    throw new IllegalArgumentException("Valore di stato del ticket non valido: " + ticketStatus);
		}
	    
	    List<Object[]> result = null;
	    try {
			result = chatRepo.countTicketsByParamsGroupByStatus(ticketId, userId, operatorId);
		} 
	    catch (Exception e) {
	    	/*
	    	 *  se scatta un eccezione qui vuol dire che la query ha restituito più di una riga: 
	    	 *  errore nei dati del db (usiamo il ticketId per fare la query, quindi deve per forza restituirne solo uno)
	    	 */		
	    	System.out.println(e.getMessage());
	    	throw new NonUniqueResultException("La query ha restituito più di un risultato: pulire il database.");
	    }
	    
	    
	    if(result == null) 
	    {
	    	/*
		     *  se il risultato è null vuol dire che il db non è pulito 
		     *  errore nei dati del db (usiamo il ticketId per fare la query, quindi deve per forza restituirne solo uno)
		     */
	    	System.out.println("riga 134");
	    	throw new NonUniqueResultException("La query ha restituito più di un risultato: pulire il database.");
	    }
	    Long count = -1l;
	    String status = "";
	    
	    for (Object[] res : result) {
	    	System.out.println("booooooooh");
	    	System.out.println("ciao ciao "+(String) res[1]);
	    	count = (Long) res[0];
		        
	        status = (String) res[1];
	        System.out.println("non c'ho voglia di vivere :D - ticket status: "+status);
	        break;
	    }
	    
	    if(count != 1l) 
	    {
	    	/*
		     *  se la query restituisce un count diverso da 1 vuol dire che il db non è pulito
		     *  errore nei dati del db (usiamo il ticketId per fare la query, quindi deve per forza restituirne solo uno)
		     */
	    	System.out.println("riga 146");
	    	throw new NonUniqueResultException("La query ha restituito più di un risultato: pulire il database.");
	    }
	    
	    if(!status.equals(ticketStatus)) 
	    {
	    	System.out.println("status1: "+ticketStatus+ "e poi: "+status);
	    	throw new IllegalArgumentException("Lo stato del ticket non è valido. Status attuale: [" + status + "] ");
	    }
	    if(!status.equals("WIP")) 
	    {
	    	System.out.println("status2: "+ticketStatus+ "e poi: "+status);
	    	throw new IllegalArgumentException("Lo stato del ticket non è valido. Status attuale: [" + status + "] ");
	    }
	}
	
	
}
