package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

	List<Optional<Chat>> findChatByTicketIdOrderByTimestampAsc(Long ticketId);

	Optional<Chat> findChatByTicketId(Long ticketId);

	@Query(value = "SELECT t.user_id, t.operator_id, t.status FROM ticket t WHERE t.id = :ticketId", nativeQuery = true)
	List<Object[]> findUserAndOperatorByTicketId(Long ticketId);

}
