package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {


	List<Ticket> findTicketByAccessCode(String accessCode);

	List<Ticket> getTicketsByStatus(String string);

	List<Ticket> findAllByOperatorId(Long operatorId);
	
	List<Ticket> findAllByStatusAndOperatorId(String status, Long operatorId);

}
