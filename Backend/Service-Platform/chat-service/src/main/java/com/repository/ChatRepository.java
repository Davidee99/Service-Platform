package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.dto.TicketInfoDTO;
import com.model.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

	List<Optional<Chat>> findChatByTicketIdOrderByTimestampAsc(Long ticketId);

	Optional<Chat> findByTicketId(Long ticketId);

	@Query(value = "SELECT t.user_id, t.operator_id, t.status FROM ticket t WHERE t.id = :ticketId", nativeQuery = true)
	List<Object[]> getInterlocutorsId(Long ticketId);
	
	@Query(value = "SELECT COUNT(*) FROM operators WHERE operator_id = :operatorId", nativeQuery = true)
    int countByOperatorId(Long operatorId);
	
	@Query(value = "SELECT COUNT(*) FROM users WHERE user_id = :userId", nativeQuery = true)
    int countByUserId(Long userId);
	
	@Query(value = "SELECT COUNT(*), STATUS  FROM ticket WHERE id = :ticketId AND user_id = :userId AND operator_id = :operatorId GROUP BY status", nativeQuery = true)
    List<Object[]> getTicketStatusStrong(@Param("ticketId") Long ticketId, @Param("userId") Long userId, @Param("operatorId") Long operatorId);
}
