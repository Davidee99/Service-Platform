package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findTicketByAccessCode(String accessCode);

	List<Ticket> getTicketsByStatus(String string);

	List<Ticket> getTicketsByStatusAndUserId(String string, Long userId);

	List<Ticket> findAllByOperatorId(Long operatorId);
	
	List<Ticket> findAllByUserId(Long userId);

	List<Ticket> findAllByStatusAndOperatorId(String status, Long operatorId);

	List<Ticket> findByStatusErrorIsNotNull();

	List<Ticket> findByOperatorIdAndStatus(Long operatorId, String status);

	/*
	 * @Modifying
	 * 
	 * @Query(value = "UPDATE Ticket t SET t.status = 'CLOSED' WHERE t.id = :id")
	 * void closeTicket(@Param("id") Long id);
	 * 
	 * @Modifying
	 * 
	 * @Query("UPDATE Ticket t SET t.type = :type WHERE t.id = :id") List<Ticket>
	 * updateTicketType(@Param("id") Long id, @Param("type") String type);
	 */

	List<Ticket> findByStatusNot(String status);

}
